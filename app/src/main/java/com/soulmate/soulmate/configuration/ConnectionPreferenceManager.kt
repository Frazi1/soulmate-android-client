package com.soulmate.soulmate.configuration

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.preference.PreferenceManager
import com.soulmate.soulmate.R

class ConnectionPreferenceManager(context: Context, resources: Resources) : IConnectionPreferenceManager {
    private val preference = PreferenceManager.getDefaultSharedPreferences(context)
    private val keyServerUrl = resources.getString(R.string.key_devPref_serverUrl)
    private val defaultServerUrl = "http://192.168.0.100:8080"

    override var serverUrl: String
        get() {
            if (!preference.contains(keyServerUrl))
                serverUrl = defaultServerUrl
            return preference.getString(keyServerUrl, defaultServerUrl)
        }
        @SuppressLint("CommitPrefEdits")
        set(value) = with(preference.edit()) {
            putString(keyServerUrl, value)
            apply()
        }
}