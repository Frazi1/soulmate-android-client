package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.AuthApi
import dtos.UserRegistrationDto
import io.reactivex.Observable
import okhttp3.ResponseBody

class AuthRepository(private val authApi: AuthApi) {

    fun registerUser(email: String, password: String): Observable<ResponseBody> {
        return authApi.registerMember(UserRegistrationDto(email, password))
    }
}