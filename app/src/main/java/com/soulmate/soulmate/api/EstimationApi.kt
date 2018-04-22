package com.soulmate.soulmate.api

import Endpoints.Companion.API_ESTIMATION
import Endpoints.Companion.API_ESTIMATION_ID_PATH
import dtos.ProfileEstimationDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path


interface EstimationApi {

    @GET("/api/estimation")
    fun getAccountsForEstimation(): Observable<Iterable<ProfileEstimationDto>>

    @POST("/api/estimation/{id}")
    fun likeProfile(@Path("id") profileId: Long): Observable<ResponseBody>
}