package com.soulmate.soulmate.presentation.view.login

import com.arellomobile.mvp.MvpView

interface LoginView : MvpView {
    fun showToast(text: String)
    fun openMainActivity()
}
