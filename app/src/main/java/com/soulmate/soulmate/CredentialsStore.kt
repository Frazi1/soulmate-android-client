package com.soulmate.soulmate

import okhttp3.Credentials

class CredentialsStore {

    companion object {
        fun getBasicAuthorizationToken(username: String, password: String): String = Credentials.basic(username, password)
    }
    var username: String = ""
        get
        private set

    var password: String = ""
        get
        private set

    var isInitialized = false
        get
        private set

    fun initialize(username: String, password: String) {
        this.username = username
        this.password = password
        isInitialized = true
    }

    fun getBasicAuthorizationToken() : String
            = Companion.getBasicAuthorizationToken(username, password)

//    init {
//        initialize("test", "test")
//    }
}