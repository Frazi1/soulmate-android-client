package com.soulmate.soulmate

import android.os.Bundle
import android.support.v4.app.FragmentActivity
import android.support.v7.app.AppCompatActivity
import com.soulmate.soulmate.ui.NavigationHelper
import com.soulmate.soulmate.ui.fragments.authorization.AuthorizationFragment
import javax.inject.Inject

class MainActivity : FragmentActivity() {
//    @Inject
//    lateinit var navigationHelper: NavigationHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        App.component.inject(this)

//        navigationHelper.navigateToAuthorization()
        setContentView(R.layout.activity_main)
    }
}
