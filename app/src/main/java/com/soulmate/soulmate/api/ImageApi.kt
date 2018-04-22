package com.soulmate.soulmate.api

import Endpoints.Companion.API_IMAGE
import dtos.ProfileImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageApi {

    @POST("/api/image")
    fun uploadProfileImage(@Body profileImage: ProfileImageDto): Observable<ResponseBody>
}