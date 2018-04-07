package com.soulmate.soulmate.ui.activity.developerSettings

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.preference.PreferenceActivity
import com.soulmate.soulmate.R


class DeveloperSettingsActivity : PreferenceActivity() {

    companion object {
        const val TAG = "DeveloperSettingsActivity"
        fun getIntent(context: Context): Intent = Intent(context, DeveloperSettingsActivity::class.java)
    }

//    @InjectPresenter
//    lateinit var mDeveloperSettingsPresenter: DeveloperSettingsPresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addPreferencesFromResource(R.xml.developer_settings)
    }
}
