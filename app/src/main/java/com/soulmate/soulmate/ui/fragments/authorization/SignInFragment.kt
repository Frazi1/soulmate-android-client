package com.soulmate.soulmate.ui.fragments.authorization


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.FragmentInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.factory
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.kodein
import com.soulmate.soulmate.R
import com.soulmate.soulmate.ui.NavigationHelper


/**
 * A simple [Fragment] subclass.
 */
class SignInFragment : Fragment(), SupportFragmentInjector {
    override val injector: KodeinInjector = KodeinInjector()

//    val navigationHelper: NavigationHelper = injector.with()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_sign_in, container, false)
    }

    fun clickLogIn() {
//        navigationHelper.navigateToAuthorization()
    }

}// Required empty public constructor
