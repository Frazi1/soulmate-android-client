package com.soulmate.soulmate.authorization

import com.fasterxml.jackson.annotation.JsonProperty

class AuthorizationToken {
    @JsonProperty(value = "access_token")
    lateinit var accessToken: String
        get
        set

    @JsonProperty(value = "refresh_token")
    lateinit var refreshToken: String
        get
        set

    @JsonProperty(value = "token_type")
    lateinit var tokenType: String
        get
        set
}