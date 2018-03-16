package com.soulmate.soulmate

import android.app.Application
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.api.AuthApi
import okhttp3.Interceptor
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
        if (credentialsStore.isInitialized) {
            val interceptor = Interceptor { chain ->
                val request = chain.request().newBuilder()
//                        .addHeader("Authorization", credentialsStore.getBasicAuthorizationToken())
                        .addHeader("Authorization", credentialsStore.getBasicAuthorizationToken())

                        .build()
                chain.proceed(request)
            }
            builder.addInterceptor(interceptor)
        }
        return builder
                .build()
    }

    fun buildRetrofit(): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://192.168.0.12:8080")
                .addConverterFactory(JacksonConverterFactory.create())
                .client(httpClient())
                .build()
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalkodein = kodein
    }
}