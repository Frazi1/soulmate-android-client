package com.soulmate.soulmate.presentation.view

import android.graphics.Bitmap
import android.net.Uri
import dtos.UserAccountDto

interface ProfileViewSoulmate : IBaseSoulmateMvpView {
    fun showImage(uri: Uri?)
    fun showImage(bitmap: Bitmap)
    fun setSpinnerVisibility(isVisible: Boolean)
    fun openLoginActivity()
    fun showProfile(userAccount: UserAccountDto)
}
