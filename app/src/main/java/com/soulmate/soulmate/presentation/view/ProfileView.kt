package com.soulmate.soulmate.presentation.view

import android.graphics.Bitmap
import android.net.Uri
import com.soulmate.soulmate.presentation.view.ISoulmateBaseMvpView
import dtos.UserAccountDto

interface ProfileView : ISoulmateBaseMvpView {
    fun showImage(uri: Uri?)
    fun showImage(bitmap: Bitmap)
    fun setSpinnerVisibility(isVisible: Boolean)
    fun openLoginActivity()
    fun showProfile(userAccount: UserAccountDto)
}
