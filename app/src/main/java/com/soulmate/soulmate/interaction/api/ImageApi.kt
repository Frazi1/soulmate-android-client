package com.soulmate.soulmate.interaction.api

import dtos.UploadImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.POST

interface ImageApi {
    companion object {
        const val IMAGE_API_PATH = "/api/image"
    }

    @POST(IMAGE_API_PATH)
    fun uploadProfileImage(@Body uploadImageDto: UploadImageDto): Observable<ResponseBody>
}