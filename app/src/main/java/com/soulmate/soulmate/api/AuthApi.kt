package com.soulmate.soulmate.interaction.api

import com.soulmate.soulmate.interaction.authorization.OAuthToken
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query


interface AuthApi {
    @POST("/oauth/token?grant_type=refresh_token")
    fun refreshToken(@Query("refresh_token") refreshToken: String,
                     @Header("Authorization") basicAuthToken: String): Observable<OAuthToken>

    @POST("/oauth/token?grant_type=password")
    fun getTokenRx(@Query("username", encoded = true) username: String,
                 @Query("password", encoded = true) password: String,
                 @Header("Authorization") basicAuthToken: String): Observable<OAuthToken>

}