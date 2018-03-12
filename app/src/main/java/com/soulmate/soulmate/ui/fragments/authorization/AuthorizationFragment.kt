package com.soulmate.soulmate.ui.fragments.authorization


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.SupportFragmentInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.R
import com.soulmate.soulmate.ui.NavigationHelper


class AuthorizationFragment : Fragment(), SupportFragmentInjector {

    override val injector: KodeinInjector = KodeinInjector()
    lateinit var buttonSignIn: Button
    lateinit var buttonSignUp: Button

    private val navigationHelper: NavigationHelper by instance()

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        initializeInjector()
//        buttonSignIn = view!!.findViewById(R.id.button_signIn)
//        buttonSignUp = view!!.findViewById(R.id.button_signUp)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_authorization, container, false)
//        buttonSignUp = view.findViewById(R.id.button_signUp)
        buttonSignIn = view.findViewById(R.id.button_signIn)
        buttonSignIn.setOnClickListener({
            navigationHelper.navigateToSignIn()
        })
        return view
    }

}// Required empty public constructor
