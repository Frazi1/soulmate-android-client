package com.soulmate.soulmate.dagger

import com.soulmate.soulmate.MainActivity
import dagger.Component
import javax.inject.Singleton


@Component(modules = arrayOf(
        AppModule::class,
        NetworkModule::class))
@Singleton
interface AppComponent {
    fun inject(mainActivity: MainActivity)
}