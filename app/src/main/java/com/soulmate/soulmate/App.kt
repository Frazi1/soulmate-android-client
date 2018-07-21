package com.soulmate.soulmate

import android.app.Application
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.configuration.AppLifeCycleObserver
import com.soulmate.soulmate.configuration.di.apiModule
import com.soulmate.soulmate.configuration.di.configurationModule
import com.soulmate.soulmate.configuration.di.repositoryModule
import com.soulmate.soulmate.configuration.interfaces.IAppLifeCycle
import android.app.NotificationManager
import android.app.NotificationChannel
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Build
import android.os.IBinder
import com.soulmate.soulmate.presentation.notifications.NotificationHelper


class App : Application(), KodeinInjected, IAppLifeCycle {
    companion object {
        lateinit var instance: App
        lateinit var globalkodein: Kodein
    }

    override val injector: KodeinInjector = KodeinInjector()

    private val appLifeCycleObserver: AppLifeCycleObserver = AppLifeCycleObserver(this)

    private val kodein by Kodein.lazy {
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
        createNotificationChannel()
        injector.inject(kodein)
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

    private fun createNotificationChannel() {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = getString(R.string.channel_name)
            val description = getString(R.string.channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(NotificationHelper.ALL_CHANNEL_ID, name, importance)
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }

    fun startNotificationService() {
        val serviceIntent = Intent(this, NotificationService::class.java)
        startService(serviceIntent)
    }

    fun stopNotificationService() {
        stopService(Intent(this, NotificationService::class.java))
    }
}