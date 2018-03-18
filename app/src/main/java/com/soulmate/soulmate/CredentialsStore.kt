package com.soulmate.soulmate

import com.soulmate.soulmate.authorization.AuthorizationToken
import okhttp3.Credentials

class CredentialsStore {

    companion object {
        private const val clientId: String = "soulmate-client"
        private const val clientSecret:String = "secret"
        fun getBasicAuthorizationToken(username: String, password: String): String = Credentials.basic(username, password)
        fun getBasicAuthorizationToken(): String = Credentials.basic(clientId, clientSecret)
    }
    lateinit var authorizationToken: AuthorizationToken


    var isTokenInitialized: Boolean = false
        get
        private set

    fun initializeWithToken(authorizationToken: AuthorizationToken) {
        this.authorizationToken = authorizationToken
        isTokenInitialized = true
    }
}