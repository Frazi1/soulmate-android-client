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
        return inflater.inflate(R.layout.fragment_authorization, container, false)
    }

    fun navigateToSignInPage(view: View){
        val findFragmentById: Fragment = fragmentManager!!.findFragmentById(R.id.authorization_fragment)
        val transaction = fragmentManager!!.beginTransaction()
        transaction.replace(view.id, findFragmentById)
        transaction.commit()
    }

}// Required empty public constructor
