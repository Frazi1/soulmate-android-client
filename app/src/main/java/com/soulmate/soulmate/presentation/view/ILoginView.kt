package com.soulmate.soulmate.presentation.view

import com.arellomobile.mvp.MvpView

interface ILoginView : MvpView {
    fun openMainActivity()
    fun openProfileActivity()
    fun setUsername(value: String)
    fun setPassword(value: String)
}
