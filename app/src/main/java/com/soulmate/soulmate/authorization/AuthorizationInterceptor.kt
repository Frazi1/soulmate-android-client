package com.soulmate.soulmate.authorization

import com.soulmate.soulmate.CredentialsStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val credentialsStore: CredentialsStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalReq = chain.request()
        val req = originalReq.newBuilder()
        if (credentialsStore.isTokenInitialized) {
            val token = credentialsStore.authorizationToken
            req.addHeader("Authorization",
                    "${credentialsStore.authorizationToken.tokenType} ${credentialsStore.authorizationToken.accessToken}")
        }
        return chain.proceed(req.build())
    }
}