package com.soulmate.soulmate.authorization

import com.fasterxml.jackson.annotation.JsonProperty

class AuthorizationToken {
    @JsonProperty(value = "access_token")
    lateinit var accessToken: String

    @JsonProperty(value = "refresh_token")
    lateinit var refreshToken: String

    @JsonProperty(value = "token_type")
    lateinit var tokenType: String

    @JsonProperty(value = "expire")
    var expiration: Long = 0
}