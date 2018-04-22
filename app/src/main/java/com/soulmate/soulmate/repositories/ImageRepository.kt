package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.ImageApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.configuration.ScheduleProvider
import dtos.ProfileImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class ImageRepository(private val imageApi: ImageApi,
                      scheduleProvider: ScheduleProvider,
                      errorHandler: IErrorHandler) : BaseRepository(errorHandler) {


    fun uploadImage(profileImageDto: ProfileImageDto): Observable<ResponseBody> {
        var result = imageApi.uploadProfileImage(profileImageDto)
        return result
    }
}