package com.soulmate.soulmate

import android.app.Application
import com.soulmate.soulmate.dagger.AppComponent
import com.soulmate.soulmate.dagger.AppModule
import com.soulmate.soulmate.dagger.DaggerAppComponent
import com.soulmate.soulmate.dagger.NetworkModule

class App : Application() {
    companion object {
        lateinit var component: AppComponent get
    }

    override fun onCreate() {
        super.onCreate()
        component = buildComponent()
    }

    protected fun buildComponent(): AppComponent {
        return DaggerAppComponent.builder()
                .appModule(AppModule(this))
                .networkModule(NetworkModule())
                .build()
    }
}