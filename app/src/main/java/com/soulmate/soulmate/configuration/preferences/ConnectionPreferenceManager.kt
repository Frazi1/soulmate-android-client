package com.soulmate.soulmate.configuration.preferences

import android.content.Context
import android.content.SharedPreferences
import android.content.res.Resources
import android.preference.PreferenceManager
import com.soulmate.soulmate.R
import com.soulmate.soulmate.configuration.interfaces.IConnectionPreferenceManager

class ConnectionPreferenceManager(context: Context, resources: Resources) : IConnectionPreferenceManager {
    private val preference: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
    private val keyServerUrl = resources.getString(R.string.key_devPref_serverUrl)
    private val keyIsHerokuServer = resources.getString(R.string.key_devPref_isHerokuServer)
    private val keyNotificationServerUrl = "devPref_notificationServerUrl"

    //local server
//    private val defaultServerUrl = "http://192.168.0.100:8080"

    //stable server
//    private val defaultServerUrl = "http://91.202.25.70:58000"

    //dev server
    private val defaultServerUrl = "http://91.202.25.70:58001"

    private val herokuServerUrl = "https://cryptic-wildwood-80958.herokuapp.com"

    override var serverUrl: String
        get() {
            if (preference.getBoolean(keyIsHerokuServer, false))
                return herokuServerUrl
            if (!preference.contains(keyServerUrl))
                serverUrl = defaultServerUrl
            return preference.getString(keyServerUrl, defaultServerUrl)
        }
        set(value) = with(preference.edit()) {
            putString(keyServerUrl, value)
            apply()
        }

    override var notificationServerUrl: String
        get() = preference.getString(keyNotificationServerUrl,
                serverUrl.replace("http", "ws", true).plus("/notifications"))
        set(value) = with(preference.edit()) {
            putString(keyNotificationServerUrl, value)
            apply()
        }
}