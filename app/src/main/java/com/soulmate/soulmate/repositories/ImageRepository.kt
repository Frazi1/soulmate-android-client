package com.soulmate.soulmate.repositories

import com.soulmate.shared.dtos.UploadImageDto
import com.soulmate.soulmate.interaction.api.ImageApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import io.reactivex.Observable
import okhttp3.ResponseBody

class ImageRepository(private val imageApi: ImageApi,
                      errorHandler: IErrorHandler) : BaseRepository(errorHandler) {


    fun uploadImage(uploadImageDto: UploadImageDto): Observable<ResponseBody> {
        return imageApi.uploadProfileImage(uploadImageDto)
    }
}