package com.soulmate.soulmate.api

import com.soulmate.dtos.UserAccountDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface AuthApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET("/users")
    fun getAllUsers(): Call<Iterable<UserAccountDto>>

    @GET("/login")
    fun checkCredentials(@Header("Authorization") authToken: String): Call<ResponseBody>
}