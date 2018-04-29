package com.soulmate.soulmate.interaction.api

import dtos.ProfileEstimationDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface EstimationApi {

    @GET("/api/estimation")
    fun getAccountsForEstimation(): Observable<Iterable<ProfileEstimationDto>>

    @POST("/api/estimation/{id}")
    fun likeProfile(@Path("id") profileId: Long): Observable<ResponseBody>

    @DELETE("/api/estimation/all")
    fun resetAllEstimations(): Observable<ResponseBody>
}