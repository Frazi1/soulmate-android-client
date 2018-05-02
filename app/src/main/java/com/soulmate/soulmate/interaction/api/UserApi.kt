package com.soulmate.soulmate.interaction.api

import dtos.UserAccountDto
import dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT

interface UserApi {
    @GET("/api/users/profile")
    fun getUserProfile(): Observable<UserAccountDto>

    @PUT("/api/users/profile")
    fun updateUserProfile(@Body userAccountDto: UserAccountDto): Observable<ResponseBody>

    @POST("api/registration")
    fun registerMember(@Body dto: UserRegistrationDto): Observable<ResponseBody>
}