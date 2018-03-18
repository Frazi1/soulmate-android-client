package com.soulmate.soulmate.api

import com.soulmate.dtos.UserAccountDto
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.*


interface AuthApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET("/users")
    fun getAllUsers(): Call<Iterable<UserAccountDto>>

    @GET("/login")
    fun checkCredentials(@Header("Authorization") authToken: String): Call<ResponseBody>

    @FormUrlEncoded
    @POST("/oauth/token")
    fun getToken(@Field("grant_type", encoded = true) grantType: RequestBody,
                 @Field("username", encoded = true) username: RequestBody,
                 @Field("password", encoded = true) password: RequestBody,
                 @Header("Authorization") basicAuthToken: String): Call<ResponseBody>

    @POST("/oauth/token")
    fun getToken(@Query("grant_type", encoded = true) grantType: String,
                 @Query("username", encoded = true) username: String,
                 @Query("password", encoded = true) password: String,
                 @Header("Authorization") basicAuthToken: String): Call<ResponseBody>

    @POST(value = "/oauth/token?grant_type=password&username=test&password=test")
    @Headers("Authorization: Basic c291bG1hdGUtY2xpZW50OnNlY3JldA==")
    fun getTokenTest(): Call<ResponseBody>
}