package com.soulmate.soulmate.configuration.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.soulmate.shared.GenderType
import com.soulmate.soulmate.configuration.interfaces.ISearchPreferencesManager

class SearchPreferencesManager(private val sharedPreferences: SharedPreferences) : ISearchPreferencesManager {
    companion object {
        private const val keyPrefix: String = "searchPref_"
    }

    override var minAge: Int = 0
        get() {
            return sharedPreferences.getInt("$keyPrefix${field::class.simpleName}", 0)
        }
        @SuppressLint("CommitPrefEdits")
        set(value) = with(sharedPreferences.edit()) {
            putInt("$keyPrefix${field::class.simpleName}", value)
            apply()
        }

    override var maxAge: Int = 100
        get() = sharedPreferences.getInt("$keyPrefix${field::class.simpleName}", 100)
        @SuppressLint("CommitPrefEdits")
        set(value) = with(sharedPreferences.edit()) {
            putInt("$keyPrefix${field::class.simpleName}", value)
            apply()
        }

    override var gender: GenderType = GenderType.NotDefined
        get() = GenderType.valueOf(sharedPreferences.getString("$keyPrefix${field::class.simpleName}", GenderType.NotDefined.name))
        @SuppressLint("CommitPrefEdits")
        set(value) = with(sharedPreferences.edit()) {
            putString("$keyPrefix${field::class.simpleName}", value.name)
            apply()
        }
}