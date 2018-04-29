package com.soulmate.soulmate.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.base.BaseActivity
import com.soulmate.soulmate.presentation.presenter.RegistrationPresenter
import com.soulmate.soulmate.presentation.view.IRegistrationView


class RegistrationActivity : BaseActivity(), IRegistrationView {
        companion object {
        const val TAG = "RegistrationActivity"
        fun getIntent(context: Context): Intent = Intent(context, RegistrationActivity::class.java)
    }

    @InjectPresenter
    lateinit var mRegistrationPresenter: RegistrationPresenter

    @BindView(R.id.registration_button_register)
    lateinit var buttonRegister: Button

    @BindView(R.id.registration_edit_email)
    lateinit var textEmail: EditText

    @BindView(R.id.registration_editText_password)
    lateinit var textPassword: EditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registration)
        ButterKnife.bind(this)
    }

    @OnClick(R.id.registration_button_register)
    fun onButtonRegisterClick() {
        mRegistrationPresenter.registerUser(textEmail.text.toString(),
                textPassword.text.toString())
    }

    override fun onSuccessfulRegistration() {
        showToast(resources.getString(R.string.successful_registration))
        openLoginActivity()
    }

    private fun openLoginActivity() {
        startActivity(LoginActivity.getIntent(this))
    }
}
