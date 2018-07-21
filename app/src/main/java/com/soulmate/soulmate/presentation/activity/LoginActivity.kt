package com.soulmate.soulmate.presentation.activity

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.widget.Button
import android.widget.TextView
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.App
import com.soulmate.soulmate.NotificationService
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.presenter.LoginPresenter
import com.soulmate.soulmate.presentation.view.ILoginView
import com.soulmate.soulmate.presentation.activity.base.BaseActivity


class LoginActivity : BaseActivity(), ILoginView {
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
        if(mLoginPresenter.attemptAutoLogin())
            onSuccessfulAuthorization()
    }

    override fun onSuccessfulAuthorization() {
        val intent = MainActivity.getIntent(this)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)

        App.instance.startNotificationService()
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
