package com.soulmate.soulmate.api

import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.authorization.AuthorizationToken
import dtos.UserRegistrationDto
import io.reactivex.Observable
import io.reactivex.Single
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface AuthApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET("/users")
    fun getAllUsers(): Call<Iterable<UserAccountDto>>

    @POST("/oauth/token?grant_type=password")
    fun getToken(@Query("username", encoded = true) username: String,
                 @Query("password", encoded = true) password: String,
                 @Header("Authorization") basicAuthToken: String): Call<AuthorizationToken>

    @POST("/oauth/token?grant_type=refresh_token")
    fun refreshToken(@Query("refresh_token") refreshToken: String,
                     @Header("Authorization") basicAuthToken: String): Call<AuthorizationToken>

    @POST("/oauth/token?grant_type=password")
    fun getTokenRx(@Query("username", encoded = true) username: String,
                 @Query("password", encoded = true) password: String,
                 @Header("Authorization") basicAuthToken: String): Observable<AuthorizationToken>

    @POST("/registration")
    fun registerMember(@Body dto: UserRegistrationDto): Observable<ResponseBody>

}