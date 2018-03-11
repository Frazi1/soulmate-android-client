package com.soulmate.soulmate.ui.fragments.authorization


import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import com.soulmate.soulmate.R


class AuthorizationFragment : Fragment() {

    lateinit var buttonSignIn: Button
    lateinit var buttonSignUp: Button

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
//        buttonSignIn = view!!.findViewById(R.id.button_signIn)
//        buttonSignUp = view!!.findViewById(R.id.button_signUp)
        // Inflate the layout for this fragment
        val view = inflater.inflate(R.layout.fragment_authorization, container, false)
        buttonSignUp = view.findViewById(R.id.button_signUp)
        buttonSignIn = view.findViewById(R.id.button_signIn)
        buttonSignIn.setOnClickListener(View.OnClickListener {
            fragmentManager!!
                    .beginTransaction()
                    .replace(R.id.container, SignInFragment())
                    .commit() })
        return view
    }

}// Required empty public constructor
