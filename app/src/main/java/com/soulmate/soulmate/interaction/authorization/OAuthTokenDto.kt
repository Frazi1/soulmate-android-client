package com.soulmate.soulmate.interaction.authorization

import java.util.*


class OAuthTokenDto(val accessToken: String,
                    val tokenType: String,
                    val refreshToken: String,
                    val expiration: Date
) {
    companion object {
        fun fromAccessToken(OAuthToken: OAuthToken): OAuthTokenDto {
            with(OAuthToken){
                val date = Date()
                date.time = expiration
                return OAuthTokenDto(accessToken, tokenType, refreshToken, date)
            }
        }
    }

    val isExpired: Boolean get() = expiration.before(Date())
}