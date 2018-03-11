package com.soulmate.soulmate.dagger

import android.content.Context
import com.soulmate.soulmate.MainActivity
import dagger.Component
import retrofit2.Retrofit
import javax.inject.Singleton


@Component(modules = [(AppModule::class), (NetworkModule::class)])
@Singleton
interface AppComponent {
    fun context(): Context
    fun retrofit(): Retrofit
    fun inject(mainActivity: MainActivity)
}