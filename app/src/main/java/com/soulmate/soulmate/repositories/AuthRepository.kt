package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.authorization.OAuthToken
import com.soulmate.soulmate.configuration.ScheduleProvider
import dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class AuthRepository(private val authApi: AuthApi,
                     scheduleProvider: ScheduleProvider,
                     errorHandler: IErrorHandler) : BaseRepository(errorHandler) {

    fun registerUser(email: String, password: String): Observable<ResponseBody> {
        return authApi.registerMember(UserRegistrationDto(email, password))
//                .observeOn(scheduleProvider.provide())
    }

    fun authorize(email: String, password: String, clientBasicAuthToken: String): Observable<OAuthToken> {
        return authApi.getTokenRx(email, password, clientBasicAuthToken)
//                .observeOn(this.scheduleProvider.provide())
    }

    fun refreshToken(refreshToken: String, clientBasicAuthToken: String): Observable<OAuthToken> {
        return authApi.refreshToken(refreshToken, clientBasicAuthToken)
    }
}