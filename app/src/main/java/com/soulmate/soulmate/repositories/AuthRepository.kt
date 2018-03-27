package com.soulmate.soulmate.repositories

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import dtos.UserRegistrationDto
import io.reactivex.Observable
import io.reactivex.functions.Consumer
import okhttp3.ResponseBody

class AuthRepository(kodein: Kodein) : BaseRepository(kodein) {
    private val authApi: AuthApi by instance()
    private val credentialsStore: CredentialsStore by instance()
    private val authorizationScheduler: AuthorizationScheduler by instance()

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