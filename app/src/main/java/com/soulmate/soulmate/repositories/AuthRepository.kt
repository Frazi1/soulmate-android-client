package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.authorization.AuthorizationToken
import com.soulmate.soulmate.configuration.ScheduleProvider
import dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class AuthRepository(private val authApi: AuthApi,
                     scheduleProvider: ScheduleProvider,
                     errorHandler: IErrorHandler) : BaseRepository(scheduleProvider, errorHandler) {

    fun registerUser(email: String, password: String): Observable<ResponseBody> {
        return authApi.registerMember(UserRegistrationDto(email, password))
                .observeOn(scheduleProvider.provide())
    }

    fun authorize(email: String, password: String, clientBasicAuthToken: String): Observable<AuthorizationToken> {
        return authApi.getTokenRx(email, password, clientBasicAuthToken)
                .observeOn(this.scheduleProvider.provide())
    }
}