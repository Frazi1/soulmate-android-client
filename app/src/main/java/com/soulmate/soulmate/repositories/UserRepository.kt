package com.soulmate.soulmate.repositories

import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.shared.dtos.UserRegistrationDto
import com.soulmate.soulmate.interaction.api.UserApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import io.reactivex.Observable
import okhttp3.ResponseBody


class UserRepository(private val userApi: UserApi,
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