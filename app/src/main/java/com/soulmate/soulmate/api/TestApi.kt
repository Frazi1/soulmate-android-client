package com.soulmate.soulmate.api

import com.soulmate.dtos.UserAccountDto
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.GET

/**
 * Created by diman on 3/4/2018.
 */
interface TestApi {
    @GET("/hello")
    fun getHelloWorldResponse(): Call<ResponseBody>

    @GET("/users")
    fun getAllUsers(): Call<Iterable<UserAccountDto>>
}