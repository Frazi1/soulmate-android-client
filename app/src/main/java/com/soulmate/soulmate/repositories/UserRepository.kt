package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.UserApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.configuration.ScheduleProvider
import dtos.UserAccountDto
import dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody


class UserRepository(private val userApi: UserApi,
                     scheduleProvider: ScheduleProvider,
                     errorHandler: IErrorHandler) : BaseRepository(errorHandler) {
    fun loadUserProfile(): Observable<UserAccountDto> {
        return userApi.getUserProfile()
    }

    fun updateUserProfile(userAccountDto: UserAccountDto): Observable<ResponseBody>
    {
        return userApi.updateUserProfile(userAccountDto)
    }

    fun registerUser(email: String, password: String): Observable<ResponseBody> {
        return userApi.registerMember(UserRegistrationDto(email, password))
    }
}