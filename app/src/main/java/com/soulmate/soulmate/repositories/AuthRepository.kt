package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.configuration.ScheduleProvider
import dtos.UserRegistrationDto
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import okhttp3.ResponseBody

class AuthRepository(private val authApi: AuthApi,
                     private val credentialsStore: CredentialsStore,
                     private val authorizationScheduler: AuthorizationScheduler,
                     scheduleProvider: ScheduleProvider,
                     errorHandler: IErrorHandler) : BaseRepository(scheduleProvider, errorHandler) {

    fun registerUser(email: String, password: String): Observable<ResponseBody> {
        return authApi.registerMember(UserRegistrationDto(email, password))
                .observeOn(scheduleProvider.provide())

    }

    fun authorize(email: String, password: String) {
        authApi.getTokenRx(email, password, CredentialsStore.getClientBasicAuthorizationToken())
                .observeOn(this.scheduleProvider.provide())
                .doOnError(Consumer(errorHandler::handle))
                .subscribe({
                    credentialsStore.initializeWithToken(it)
                    authorizationScheduler.startAuthorizationTask(AuthorizationScheduler.REFRESH_TOKEN_PERIOD)
                }, {})
    }
}