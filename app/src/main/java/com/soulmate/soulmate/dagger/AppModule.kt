package com.soulmate.soulmate.dagger

import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton


@Module
class AppModule() {
    lateinit var appContext: Context

    @Singleton
    @Provides
    fun provideContext(): Context {
        return appContext
    }
}