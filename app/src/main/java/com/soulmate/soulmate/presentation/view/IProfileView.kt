package com.soulmate.soulmate.presentation.view

import android.graphics.Bitmap
import android.net.Uri
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.presentation.view.base.IBaseMvpView
import com.soulmate.soulmate.presentation.view.base.ILoader

interface IProfileView : IBaseMvpView, ILoader {
    fun showImage(uri: Uri?)
    fun showImage(bitmap: Bitmap)
    fun openLoginActivity()
    fun showProfile(userAccount: UserAccountDto)
}
