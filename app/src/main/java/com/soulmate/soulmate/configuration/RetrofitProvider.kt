package com.soulmate.soulmate.configuration

import com.fasterxml.jackson.databind.ObjectMapper
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.soulmate.soulmate.configuration.interfaces.IConnectionPreferenceManager
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class RetrofitProvider(private val objectMapper: ObjectMapper,
                       private val credentialsStore: CredentialsStore,
                       private val connectionPreferenceManager: IConnectionPreferenceManager,
                       private val httpClient: OkHttpClient) {


    private var retrofitInstance: Retrofit? = null

    fun provide(): Retrofit {
        if (retrofitInstance == null) {
            retrofitInstance = buildRetrofit(objectMapper)
        }
        return retrofitInstance!!
    }



    private fun buildRetrofit(objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
                .baseUrl(connectionPreferenceManager.serverUrl)
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient)
                .build()
    }
}