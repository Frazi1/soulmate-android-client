package com.soulmate.soulmate.configuration

import android.app.Activity
import android.app.Application
import android.content.ComponentCallbacks2
import android.content.Context
import android.os.Bundle
import com.soulmate.soulmate.configuration.interfaces.IAppLifeCycle

abstract class LifeCycleObserver(private val appContext: Context,
                                 private val appLifeCycle: IAppLifeCycle) : Application.ActivityLifecycleCallbacks, ComponentCallbacks2 {
    private var isInBackground: Boolean = true

    override fun onActivityResumed(activity: Activity?) {
        if (isInBackground) {
            isInBackground = false
            appLifeCycle.onGoToForeground()
        }
    }

    override fun onActivityStarted(activity: Activity?) {
        if (isInBackground) {
            isInBackground = false
            appLifeCycle.onGoToForeground()
        }
    }

    override fun onActivityStopped(activity: Activity?) {
    }

    override fun onActivityPaused(activity: Activity?) {
    }

    override fun onActivityDestroyed(activity: Activity?) {
    }

    override fun onActivitySaveInstanceState(activity: Activity?, outState: Bundle?) {
    }

    override fun onActivityCreated(activity: Activity?, savedInstanceState: Bundle?) {
//        if (isInBackground) {
//            isInBackground = false
//            appLifeCycle.onGoToForeground()
//        }
    }

    override fun onTrimMemory(level: Int) {
//        if (level == ComponentCallbacks2.TRIM_MEMORY_UI_HIDDEN) {
            isInBackground = true
            appLifeCycle.onGoToBackground()
//        }
    }
}

