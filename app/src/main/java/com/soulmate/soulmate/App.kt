package com.soulmate.soulmate

import android.app.Application
import com.github.salomonbrys.kodein.*
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class App : Application(), KodeinAware {

    override val kodein by Kodein.lazy {
        bind<Retrofit>() with singleton {
            Retrofit.Builder()
                    .baseUrl("http://192.168.0.12:8080")
                    .addConverterFactory(JacksonConverterFactory.create())
                    .build()
        }
    }

    override fun onCreate() {
        super.onCreate()
    }
}