package com.soulmate.soulmate.repositories

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.api.ImageApi
import dtos.ProfileImageDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class ImageRepository(kodein: Kodein) : BaseRepository(kodein) {

    private val imageApi: ImageApi by instance()

    fun uploadImage(profileImageDto: ProfileImageDto): Observable<ResponseBody> {
        var result = imageApi.uploadProfileImage(profileImageDto)
        return result
    }
}