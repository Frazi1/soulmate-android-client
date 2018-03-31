package com.soulmate.soulmate

import android.content.SharedPreferences
import com.soulmate.soulmate.authorization.AccessTokenDto
import com.soulmate.soulmate.authorization.AuthorizationToken
import okhttp3.Credentials
import java.util.*

class CredentialsStore(private val settings: SharedPreferences) {

    companion object {
        private const val usernameKey: String = "username"
        private const val passwordKey: String = "password"
        private const val accessTokenKey: String = "access_token"
        private const val refreshTokenKey: String = "refresh_token"
        private const val tokenTypeKey: String = "token_type"
        private const val expirationKey: String = "expiration"

        private const val clientId: String = "soulmate-client"
        private const val clientSecret: String = "secret"
        fun getClientBasicAuthorizationToken(): String = Credentials.basic(clientId, clientSecret)
    }

    init {
//        if (settings.contains(usernameKey) and settings.contains(passwordKey))
        initializeWithCredentialsInternal(
                settings.getString(usernameKey, ""),
                settings.getString(passwordKey, ""))

        initializeWithToken(with(settings) {
            val date = Date()
            date.time = getLong(expirationKey, 0)
            return@with AccessTokenDto(
                    getString(accessTokenKey, ""),
                    getString(refreshTokenKey, ""),
                    getString(tokenTypeKey, ""),
                    date
            )
        })
    }

    lateinit var authorizationToken: AccessTokenDto

    lateinit var username: String
        private set

    lateinit var password: String
        private set


    var isTokenInitialized: Boolean = false
        private set

    var isCredentialsInitialized: Boolean = false
        private set

    fun initializeWithToken(authorizationToken: AccessTokenDto) {
        initializeWithTokenInternal(authorizationToken)
        with(settings.edit()) {
            putString(accessTokenKey, authorizationToken.accessToken)
            putString(tokenTypeKey, authorizationToken.tokenType)
            putString(refreshTokenKey, authorizationToken.refreshToken)
            putLong(expirationKey, authorizationToken.expiration.time)
            apply()
        }
    }

    private fun initializeWithTokenInternal(authorizationToken: AccessTokenDto) {
        this.authorizationToken = authorizationToken
        this.isTokenInitialized = true
    }

    fun initializeWithCredentials(username: String, password: String) {
        initializeWithCredentialsInternal(username, password)
        with(settings.edit()) {
            putString(usernameKey, username)
            putString(passwordKey, password)
            apply()
        }
    }

    private fun initializeWithCredentialsInternal(username: String, password: String) {
        this.username = username
        this.password = password
        isCredentialsInitialized = true
    }
}