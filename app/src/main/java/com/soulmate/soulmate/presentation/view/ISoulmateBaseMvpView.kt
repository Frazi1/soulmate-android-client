package com.soulmate.soulmate.presentation.view

import com.arellomobile.mvp.MvpView

interface ISoulmateBaseMvpView : MvpView {
    fun showToast(text: String, duration: Int)
    fun showToast(text: String)

}