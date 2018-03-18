package com.soulmate.soulmate

import okhttp3.Credentials

class CredentialsStore {

    companion object {
        private const val clientId: String = "soulmate-client"
        private const val clientSecret:String = "secret"
        fun getBasicAuthorizationToken(username: String, password: String): String = Credentials.basic(username, password)
        fun getBasicAuthorizationToken(): String = Credentials.basic(clientId, clientSecret)
    }
    var accessToken: String = ""
        get
        private set

    var refreshToken = ""
        get
        private set

    var isTokenInitialized: Boolean = false
        get
        private set

    fun initializeWithToken(accessToken: String, refreshToken: String) {
        this.accessToken = accessToken
        this.refreshToken = refreshToken
        isTokenInitialized = true
    }

    fun getOAuth2AuthorizationToken(): String = accessToken

//    init {
//        initialize("test", "test")
//    }
}