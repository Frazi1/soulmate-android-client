package com.soulmate.soulmate.dagger

import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import javax.inject.Singleton

@Module
class NetworkModule {
    companion object {
        const val BASE_URL: String = "http://192.168.0.12:8080";
    }

    @Provides
    @Singleton
    fun getRetrofit(): Retrofit {
        return Retrofit.Builder()
                .addConverterFactory(JacksonConverterFactory.create())
                .baseUrl(BASE_URL)
                .build()
    }

//    @Provides
//    @Singleton
//    fun getUserRepository(): UserAccountRepository{
//        return UserAccountRepository()
//    }
}