package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.ProfileApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.configuration.ScheduleProvider
import dtos.UserAccountDto
import io.reactivex.Observable
import okhttp3.ResponseBody


class UserRepository(private val profileApi: ProfileApi,
                     scheduleProvider: ScheduleProvider,
                     errorHandler: IErrorHandler) : BaseRepository(errorHandler) {
    fun loadUserProfile(): Observable<UserAccountDto> {
        return profileApi.getUserProfile()
    }

    fun updateUserProfile(userAccountDto: UserAccountDto): Observable<ResponseBody>
    {
        return profileApi.updateUserProfile(userAccountDto)
    }
}