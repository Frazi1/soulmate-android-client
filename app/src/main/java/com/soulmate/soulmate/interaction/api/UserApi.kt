package com.soulmate.soulmate.interaction.api

import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.shared.dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*

interface UserApi {
    @GET("/api/users/profile")
    fun getOwnProfile(): Observable<UserAccountDto>

    @GET("api/users")
    fun getUserProfile(@Query("ids") userIds: List<Long>): Observable<List<UserAccountDto>>

    @PUT("/api/users/profile")
    fun updateUserProfile(@Body userAccountDto: UserAccountDto): Observable<ResponseBody>

    @POST("api/registration")
    fun registerMember(@Body dto: UserRegistrationDto): Observable<ResponseBody>
}