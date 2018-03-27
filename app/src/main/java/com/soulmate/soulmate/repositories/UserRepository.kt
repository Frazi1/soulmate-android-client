package com.soulmate.soulmate.repositories

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.api.ProfileApi
import dtos.UserAccountDto
import io.reactivex.Observable


class UserRepository(kodein: Kodein) : BaseRepository(kodein) {
    private val profileApi: ProfileApi by instance()
    fun loadUserProfile(): Observable<UserAccountDto> {
        return profileApi.getUserProfile()
    }
}