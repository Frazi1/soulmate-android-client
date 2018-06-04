package com.soulmate.soulmate.presentation.activity.base

import android.view.Menu
import android.view.MenuItem
import com.arellomobile.mvp.MvpAppCompatActivity
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.view.IBaseMvpView
import com.soulmate.soulmate.presentation.activity.DeveloperSettingsActivity

abstract class BaseActivity : MvpAppCompatActivity(), IBaseMvpView {
    protected val baseContext: BaseContext = BaseContext(this)

    override fun showToast(text: String, duration: Int) {
        baseContext.showToast(text, duration)
    }

    override fun showToast(text: String) {
        baseContext.showToast(text)
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