package com.soulmate.soulmate.ui.activity.login

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.MainActivity
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.presenter.login.LoginPresenter
import com.soulmate.soulmate.presentation.view.login.LoginView
import com.soulmate.soulmate.ui.activity.profile.ProfileActivity
import com.soulmate.soulmate.ui.activity.registration.RegistrationActivity


class LoginActivity : MvpAppCompatActivity(), LoginView {
    companion object {
        const val TAG = "LoginActivity"
        fun getIntent(context: Context): Intent = Intent(context, LoginActivity::class.java)
    }

    @InjectPresenter
    lateinit var mLoginPresenter: LoginPresenter

    private lateinit var buttonLogin: Button
    private lateinit var buttonRegistration: Button
    private lateinit var textEmail: TextView
    private lateinit var textPassword: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        buttonLogin = findViewById(R.id.login_button_login)
        buttonRegistration = findViewById(R.id.login_button_registration)
        textEmail = findViewById(R.id.login_text_email)
        textPassword = findViewById(R.id.login_text_password)

        buttonLogin.setOnClickListener {
            mLoginPresenter.attemptLogin(
                    textEmail.text.toString(),
                    textPassword.text.toString())
        }

        buttonRegistration.setOnClickListener { startActivity(RegistrationActivity.getIntent(this)) }
    }

    override fun openProfileActivity() {
        startActivity(ProfileActivity.getIntent(this))
    }

    override fun openMainActivity() {
        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}
