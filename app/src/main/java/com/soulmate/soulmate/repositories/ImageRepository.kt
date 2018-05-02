package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.interaction.api.ImageApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import dtos.UploadImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class ImageRepository(private val imageApi: ImageApi,
                      errorHandler: IErrorHandler) : BaseRepository(errorHandler) {


    fun uploadImage(uploadImageDto: UploadImageDto): Observable<ResponseBody> {
        var result = imageApi.uploadProfileImage(uploadImageDto)
        return result
    }
}