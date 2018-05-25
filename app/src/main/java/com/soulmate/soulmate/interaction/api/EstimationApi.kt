package com.soulmate.soulmate.interaction.api

import com.soulmate.shared.Estimation
import com.soulmate.shared.GenderType
import com.soulmate.shared.dtos.UserAccountDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.*


interface EstimationApi {

    @GET("/api/estimation")
    fun getUsersForEstimation(@Query("ageFrom") ageFrom: Int? = null,
                              @Query("ageTo") ageTo: Int? = null,
                              @Query("gender") gender: MutableList<GenderType>? = null): Observable<Iterable<UserAccountDto>>

    @POST("/api/estimation/{id}")
    fun estimateUser(
            @Path("id") userId: Long,
            @Query("estimation") estimation: Estimation
    ): Observable<ResponseBody>

    @DELETE("/api/estimation/all")
    fun resetAllEstimations(): Observable<ResponseBody>
}