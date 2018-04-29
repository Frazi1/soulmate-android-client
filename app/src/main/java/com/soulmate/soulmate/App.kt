package com.soulmate.soulmate

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinAware
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.configuration.AppLifeCycleObserver
import com.soulmate.soulmate.configuration.interfaces.IAppLifeCycle
import com.soulmate.soulmate.configuration.di.apiModule
import com.soulmate.soulmate.configuration.di.configurationModule
import com.soulmate.soulmate.configuration.di.repositoryModule

class App : Application(), KodeinAware, IAppLifeCycle {
    companion object {
        lateinit var instance: App
        lateinit var globalkodein: Kodein
    }

    private val appLifeCycleObserver: AppLifeCycleObserver = AppLifeCycleObserver(this)

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@App))
        import(apiModule)
        import(repositoryModule)
        import(configurationModule(this@App))
    }

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalkodein = kodein
        registerActivityLifecycleCallbacks(appLifeCycleObserver)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        appLifeCycleObserver.onTrimMemory(level)
    }

    override fun onGoToForeground() {
//        authorizationScheduler.startAuthorizationTask()
    }

    override fun onGoToBackground() {
//        authorizationScheduler.stopAuthorizationTask()
    }
}