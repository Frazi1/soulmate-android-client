package com.soulmate.soulmate.configuration

import android.content.res.Configuration
import com.soulmate.soulmate.App

class AppLifeCycleObserver(app: App) : LifeCycleObserver(app, app) {
    override fun onLowMemory() {
    }

    override fun onConfigurationChanged(newConfig: Configuration?) {
    }
}