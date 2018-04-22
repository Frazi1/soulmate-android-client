package com.soulmate.soulmate.api

import Endpoints.Companion.API_USERS
import Endpoints.Companion.USER_PROFILE_PATH

import dtos.UserAccountDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PUT

interface ProfileApi {
    @GET("/api/users/profile")
    fun getUserProfile(): Observable<UserAccountDto>

    @PUT("/api/users/profile")
    fun updateUserProfile(@Body userAccountDto: UserAccountDto): Observable<ResponseBody>
}