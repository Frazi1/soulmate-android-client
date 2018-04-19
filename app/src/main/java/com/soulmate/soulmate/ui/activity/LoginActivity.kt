package com.soulmate.soulmate.ui.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.presenter.LoginPresenter
import com.soulmate.soulmate.presentation.view.LoginView
import com.soulmate.soulmate.ui.activity.base.BaseActivity


class LoginActivity : BaseActivity(), LoginView {
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
//        mLoginPresenter.attemptAutoLogin()
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
        val intent = MainActivity.getIntent(this)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun openMainActivity() {
//        startActivity(Intent(this, MainActivity::class.java))
    }

    override fun setUsername(value: String) {
        textEmail.text = value
    }

    override fun setPassword(value: String) {
        textPassword.text = value
    }
}
