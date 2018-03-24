package com.soulmate.soulmate.api

import dtos.ProfileImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageApi {

    @POST("/image")
    fun uploadProfileImage(@Body profileImage: ProfileImageDto): Observable<ResponseBody>
}