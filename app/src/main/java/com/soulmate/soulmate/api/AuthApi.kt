package com.soulmate.soulmate.api

import Endpoints.Companion.API_REGISTRATION
import Endpoints.Companion.API_USERS
import com.soulmate.soulmate.authorization.OAuthToken
import dtos.UserAccountDto
import dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface AuthApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET(API_USERS)
    fun getAllUsers(): Call<Iterable<UserAccountDto>>

    @POST("/oauth/token?grant_type=refresh_token")
    fun refreshToken(@Query("refresh_token") refreshToken: String,
                     @Header("Authorization") basicAuthToken: String): Observable<OAuthToken>

    @POST("/oauth/token?grant_type=password")
    fun getTokenRx(@Query("username", encoded = true) username: String,
                 @Query("password", encoded = true) password: String,
                 @Header("Authorization") basicAuthToken: String): Observable<OAuthToken>

    @POST(API_REGISTRATION)
    fun registerMember(@Body dto: UserRegistrationDto): Observable<ResponseBody>

}