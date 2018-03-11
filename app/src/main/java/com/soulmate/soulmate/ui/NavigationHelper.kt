package com.soulmate.soulmate.ui

import android.support.v4.app.FragmentManager
import com.soulmate.soulmate.MainActivity
import com.soulmate.soulmate.R
import com.soulmate.soulmate.ui.fragments.authorization.AuthorizationFragment
import com.soulmate.soulmate.ui.fragments.authorization.SignInFragment
import javax.inject.Inject


class NavigationHelper @Inject constructor(mainActivity: MainActivity) {

    private val containerId: Int = R.id.container
    private val fragmentManager: FragmentManager = mainActivity.supportFragmentManager

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