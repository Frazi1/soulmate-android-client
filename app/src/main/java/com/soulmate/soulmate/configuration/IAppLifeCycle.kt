package com.soulmate.soulmate.configuration

interface IAppLifeCycle {
    fun onGoToForeground()
    fun onGoToBackground()
}