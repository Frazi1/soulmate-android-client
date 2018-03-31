package com.soulmate.soulmate.authorization

import android.annotation.TargetApi
import android.os.Build
import java.time.Instant
import java.util.*


class AccessTokenDto(val accessToken: String,
                     val tokenType: String,
                     val refreshToken: String,
                     val expiration: Date
) {
    companion object {
        fun fromAccessToken(authorizationToken: AuthorizationToken): AccessTokenDto {
            with(authorizationToken){
                val date = Date()
                date.time = expiration
                return AccessTokenDto(accessToken, tokenType,refreshToken, date)
            }
        }
    }

    val isExpired: Boolean get() = expiration.before(Date())
}