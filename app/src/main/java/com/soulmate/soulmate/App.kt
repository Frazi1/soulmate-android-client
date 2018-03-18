package com.soulmate.soulmate

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.authorization.AuthorizationInterceptor
import com.soulmate.soulmate.configuration.JacksonModule
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class App : Application(), KodeinAware {

    companion object {
        lateinit var instance: App
        lateinit var globalkodein: Kodein
    }

    private val retrofit: Retrofit by lazy {
        buildRetrofit()
    }

    override val kodein = Kodein {
        bind<Retrofit>() with provider { buildRetrofit() }
        bind<CredentialsStore>() with singleton { CredentialsStore() }
        bind<AuthApi>() with factory { a: Retrofit -> a.create(AuthApi::class.java) }
    }

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        val credentialsStore: CredentialsStore = kodein.instance()
        builder.addInterceptor(AuthorizationInterceptor(credentialsStore))
        return builder.build()
    }

    fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://192.168.0.12:8080")
                .addConverterFactory(JacksonConverterFactory.create(JacksonModule.objectMapper))
                .client(httpClient())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalkodein = kodein
    }
}