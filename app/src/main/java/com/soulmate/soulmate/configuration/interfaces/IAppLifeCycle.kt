package com.soulmate.soulmate.configuration.interfaces

interface IAppLifeCycle {
    fun onGoToForeground()
    fun onGoToBackground()
}