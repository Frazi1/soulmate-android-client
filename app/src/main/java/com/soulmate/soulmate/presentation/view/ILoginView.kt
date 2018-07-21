package com.soulmate.soulmate.presentation.view

import com.arellomobile.mvp.MvpView

interface ILoginView : MvpView {
    fun openMainActivity()
    fun onSuccessfulAuthorization()
    fun setUsername(value: String)
    fun setPassword(value: String)
}
