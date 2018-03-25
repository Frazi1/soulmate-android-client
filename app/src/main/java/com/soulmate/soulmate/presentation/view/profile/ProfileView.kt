package com.soulmate.soulmate.presentation.view.profile

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView
import com.soulmate.soulmate.presentation.view.ISoulmateBaseMvpView

interface ProfileView : ISoulmateBaseMvpView {
    fun setUsername(name: String?)
    fun showImage(bitmap: Bitmap)
    fun setSpinnerVisibility(isVisible: Boolean)
}
