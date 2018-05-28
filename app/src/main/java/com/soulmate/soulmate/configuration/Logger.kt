package com.soulmate.soulmate.configuration

import android.util.Log
import com.soulmate.soulmate.configuration.interfaces.ILogger

class Logger: ILogger {
    override fun error(text: String) {
        Log.e("Soulmate", text)
    }
}