package com.soulmate.soulmate.interaction.authorization

import com.soulmate.soulmate.configuration.CredentialsStore
import okhttp3.*

class AuthorizationInterceptor(private val credentialsStore: CredentialsStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalReq = chain.request()
        val req = originalReq.newBuilder()
        if (credentialsStore.isTokenInitialized) {
            req.addHeader("Authorization",
                    "${credentialsStore.authorizationToken.tokenType} ${credentialsStore.authorizationToken.accessToken}")
        }
        return chain.proceed(req.build())
    }
}

