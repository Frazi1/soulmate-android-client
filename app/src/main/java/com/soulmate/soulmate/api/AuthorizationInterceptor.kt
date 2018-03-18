package com.soulmate.soulmate.api

import com.soulmate.soulmate.CredentialsStore
import okhttp3.Interceptor
import okhttp3.Response

class AuthorizationInterceptor(private val credentialsStore: CredentialsStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val originalReq = chain.request()
//        val url = originalReq.url().newBuilder()
//                .addEncodedQueryParameter("username", "test")
//                .addEncodedQueryParameter("password", "test")
//                .addEncodedQueryParameter("grant_type", "password")
//                .build()
        val req = originalReq.newBuilder()
//                .addHeader("Authorization", "Basic c291bG1hdGUtY2xpZW50OnNlY3JldA==")
//                .url(url)
                .build()
//        if(credentialsStore.isTokenInitialized) {
//            req.addHeader("Authorization", "Bearer ${credentialsStore.getOAuth2AuthorizationToken()}")
//        }
        return chain.proceed(req)
    }

}