package com.soulmate.soulmate

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.soulmate.soulmate.authorization.AuthorizationInterceptor
import com.soulmate.soulmate.configuration.IConnectionPreferenceManager
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class RetrofitProvider(private val objectMapper: ObjectMapper,
                       private val credentialsStore: CredentialsStore,
                       private val connectionPreferenceManager: IConnectionPreferenceManager) {


    private var retrofitInstance: Retrofit? = null

    fun provide(): Retrofit {
        if (retrofitInstance == null) {
            retrofitInstance = buildRetrofit(objectMapper)
        }
        return retrofitInstance!!
    }

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.addInterceptor(AuthorizationInterceptor(credentialsStore))
//        builder.authenticator(TokenAuthenticator(kodein.lazy))
        return builder.build()
    }

    private fun buildRetrofit(objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
                .baseUrl(connectionPreferenceManager.serverUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient())
                .build()
    }
}