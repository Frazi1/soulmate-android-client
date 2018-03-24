package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.ImageApi
import dtos.ProfileImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class ImageRepository(private val imageApi: ImageApi) : BaseRepository() {

    fun uploadImage(profileImageDto: ProfileImageDto): Observable<ResponseBody>{
        var result = imageApi.uploadProfileImage(profileImageDto)
        return result
    }
}