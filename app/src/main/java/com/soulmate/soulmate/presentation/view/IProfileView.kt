package com.soulmate.soulmate.presentation.view

import android.graphics.Bitmap
import android.net.Uri
import dtos.UserAccountDto

interface IProfileView : IBaseMvpView, ILoader {
    fun showImage(uri: Uri?)
    fun showImage(bitmap: Bitmap)
    fun openLoginActivity()
    fun showProfile(userAccount: UserAccountDto)
}
