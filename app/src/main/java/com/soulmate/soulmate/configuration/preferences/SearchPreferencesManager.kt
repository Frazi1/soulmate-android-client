package com.soulmate.soulmate.configuration.preferences

import android.annotation.SuppressLint
import android.content.SharedPreferences
import com.soulmate.shared.GenderType
import com.soulmate.soulmate.configuration.interfaces.ISearchPreferencesManager

class SearchPreferencesManager(private val sharedPreferences: SharedPreferences) : ISearchPreferencesManager {
    companion object {
        private const val keyPrefix: String = "searchPref_"
        private const val keyMinAge: String="minAge"
        private const val keyMaxAge: String="maxAge"
        private const val keyGender: String="gender"

    }

    override var minAge: Int
        get() {
            return sharedPreferences.getInt("$keyPrefix$keyMinAge", 0)
        }
        @SuppressLint("CommitPrefEdits")
        set(value) = with(sharedPreferences.edit()) {
            putInt("$keyPrefix$keyMinAge", value)
            apply()
        }

    override var maxAge: Int
        get() = sharedPreferences.getInt("$keyPrefix$keyMaxAge", 100)
        @SuppressLint("CommitPrefEdits")
        set(value) = with(sharedPreferences.edit()) {
            putInt("$keyPrefix$keyMaxAge", value)
            apply()
        }

    override val genderTypes: List<GenderType> = listOf(GenderType.Female) //TODO: implement
//    override var gender: GenderType
//        get() = GenderType.valueOf(sharedPreferences.getString("$keyPrefix$keyGender", GenderType.NotDefined.name))
//        @SuppressLint("CommitPrefEdits")
//        set(value) = with(sharedPreferences.edit()) {
//            putString("$keyPrefix$keyGender", value.name)
//            apply()
//        }
}