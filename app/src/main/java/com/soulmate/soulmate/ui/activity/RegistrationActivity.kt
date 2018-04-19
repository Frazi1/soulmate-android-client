package com.soulmate.soulmate.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.presenter.RegistrationPresenter
import com.soulmate.soulmate.presentation.view.RegistrationViewSoulmate
import com.soulmate.soulmate.ui.activity.base.BaseActivity


class RegistrationActivity : BaseActivity(), RegistrationViewSoulmate {
    companion object {
        const val TAG = "RegistrationActivity"
        fun getIntent(context: Context): Intent = Intent(context, RegistrationActivity::class.java)
    }

    @InjectPresenter
    lateinit var mRegistrationPresenter: RegistrationPresenter

    lateinit var buttonRegister: Button
    lateinit var textEmail: EditText
    lateinit var textPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)

        buttonRegister = findViewById(R.id.registration_button_register)
        textEmail = findViewById(R.id.registration_edit_email)
        textPassword = findViewById(R.id.registration_editText_password)

        buttonRegister.setOnClickListener {
            mRegistrationPresenter.registerUser(textEmail.text.toString(),
                    textPassword.text.toString())
        }
    }

    override fun openLoginActivity() {
        startActivity(LoginActivity.getIntent(this))
    }
}
