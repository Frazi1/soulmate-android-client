package com.soulmate.soulmate.ui

import android.support.v4.app.FragmentManager
import com.soulmate.soulmate.MainActivity
import com.soulmate.soulmate.R
import com.soulmate.soulmate.ui.fragments.authorization.AuthorizationFragment
import com.soulmate.soulmate.ui.fragments.authorization.SignInFragment
import javax.inject.Inject


class NavigationHelper {

    @Inject constructor(mainActivity: MainActivity) {
        this.fragmentManager = mainActivity.supportFragmentManager
    }

    private val containerId: Int = R.id.container
    private val fragmentManager: FragmentManager

    fun navigateToSignIn() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, SignInFragment())
                .commit()
    }

    fun navigateToAuthorization() {
        fragmentManager
                .beginTransaction()
                .replace(containerId, AuthorizationFragment())
                .commit()
    }
}