package com.soulmate.soulmate

import com.soulmate.soulmate.authorization.AuthorizationToken
import okhttp3.Credentials

class CredentialsStore {

    companion object {
        private const val clientId: String = "soulmate-client"
        private const val clientSecret: String = "secret"
        fun getClientBasicAuthorizationToken(): String = Credentials.basic(clientId, clientSecret)
    }

    lateinit var authorizationToken: AuthorizationToken

    lateinit var username: String
        get
        private set

    lateinit var password: String
        get
        private set


    var isTokenInitialized: Boolean = false
        get
        private set

    var isCredentialsInitialized: Boolean = false
        get
        private set

    fun initializeWithToken(authorizationToken: AuthorizationToken) {
        this.authorizationToken = authorizationToken
        this.isTokenInitialized = true
    }

    fun initializeWithCredentials(username: String, password: String) {
        this.username = username
        this.password = password
        isCredentialsInitialized = true
    }
}