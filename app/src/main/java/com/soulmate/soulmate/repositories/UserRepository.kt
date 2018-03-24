package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.ProfileApi
import dtos.UserAccountDto
import io.reactivex.Observable


class UserRepository(private val profileApi: ProfileApi) : BaseRepository() {

    fun loadUserProfile(): Observable<UserAccountDto> {
        return profileApi.getUserProfile()
    }
}