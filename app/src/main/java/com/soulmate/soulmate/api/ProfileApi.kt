package com.soulmate.soulmate.api

import dtos.UserAccountDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {
    @GET("/profile")
    fun getUserProfile(): Observable<UserAccountDto>

    @PUT("/profile")
    fun updateUserProfile(@Body userAccountDto: UserAccountDto) : Observable<ResponseBody>
}