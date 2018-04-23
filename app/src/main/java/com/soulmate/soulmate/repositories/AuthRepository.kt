package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.interaction.api.AuthApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.interaction.authorization.OAuthToken
import io.reactivex.Observable

class AuthRepository(private val authApi: AuthApi,
                     errorHandler: IErrorHandler) : BaseRepository(errorHandler) {

    fun authorize(email: String, password: String, clientBasicAuthToken: String): Observable<OAuthToken> {
        return authApi.getTokenRx(email, password, clientBasicAuthToken)
//                .observeOn(this.scheduleProvider.provide())
    }

    fun refreshToken(refreshToken: String, clientBasicAuthToken: String): Observable<OAuthToken> {
        return authApi.refreshToken(refreshToken, clientBasicAuthToken)
    }
}