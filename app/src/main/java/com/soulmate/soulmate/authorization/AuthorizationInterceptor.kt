package com.soulmate.soulmate.authorization

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import okhttp3.*
import retrofit2.Retrofit

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

//class TokenAuthenticator(private val kodein: Kodein) : Authenticator {
//    private val credentialsStore: CredentialsStore by lazy { kodein.instance<CredentialsStore>() }
//    private val retrofit: Retrofit by lazy { kodein.instance<Retrofit>() }
//
//    override fun authenticate(route: Route, response: Response): Request? {
//        val authApi = retrofit.create(AuthApi::class.java)

//        if (credentialsStore.isCredentialsInitialized) {

//            val newToken: AuthorizationToken =
//                    credentialsStore.authorizationToken
//                    if (credentialsStore.isTokenInitialized)
//                        authApi.refreshToken(
//                                credentialsStore.authorizationToken.refreshToken,
//                                CredentialsStore.getClientBasicAuthorizationToken())
//                                .execute()
//                                .body()!!
//                    else
//                        authApi.getToken(
//                                credentialsStore.username,
//                                credentialsStore.password,
//                                CredentialsStore.getClientBasicAuthorizationToken())
//                                .execute().body()!!
//
//            credentialsStore.initializeWithToken(newToken)
//            return response.request().newBuilder()
//                    .addHeader("Authorization", "${newToken.tokenType} ${newToken.accessToken}")
//                    .build()

//       }
//        else {
//            return null
//        }
//    }
//}