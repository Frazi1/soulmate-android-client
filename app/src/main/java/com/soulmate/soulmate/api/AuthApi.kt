package com.soulmate.soulmate.api

import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.authorization.AuthorizationToken
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface AuthApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET("/users")
    fun getAllUsers(): Call<Iterable<UserAccountDto>>

    @POST("/oauth/authorizationToken")
    fun getToken(@Query("grant_type", encoded = true) grantType: String,
                 @Query("username", encoded = true) username: String,
                 @Query("password", encoded = true) password: String,
                 @Header("Authorization") basicAuthToken: String): Call<AuthorizationToken>
}