package com.soulmate.soulmate.ui.activity

import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.view.ISoulmateBaseMvpView
import com.soulmate.soulmate.ui.activity.developerSettings.DeveloperSettingsActivity

abstract class BaseSoulmateActivity : MvpAppCompatActivity(), ISoulmateBaseMvpView {
    override fun showToast(text: String, duration: Int) {
        Toast.makeText(this, text, duration).show()
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_developer, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item != null) {
            when (item.itemId) {
                R.id.button_menu_developer -> openDeveloperSettings()
                else -> return false
            }
            return true
        }
        return false
    }

    protected fun openDeveloperSettings() {
        startActivity(DeveloperSettingsActivity.getIntent(this))
    }
}