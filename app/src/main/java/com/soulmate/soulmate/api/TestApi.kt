package com.soulmate.soulmate.api

import com.soulmate.dtos.UserAccountDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

interface TestApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET("/users")
    fun getAllUsers(): Call<Iterable<UserAccountDto>>
}