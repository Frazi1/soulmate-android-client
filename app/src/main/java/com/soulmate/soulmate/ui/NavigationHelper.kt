package com.soulmate.soulmate.ui

import android.support.v4.app.ActivityCompat
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import com.soulmate.soulmate.MainActivity
import com.soulmate.soulmate.R
import com.soulmate.soulmate.ui.fragments.authorization.AuthorizationFragment
import com.soulmate.soulmate.ui.fragments.authorization.SignInFragment


class NavigationHelper constructor(mainActivity: MainActivity) {

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