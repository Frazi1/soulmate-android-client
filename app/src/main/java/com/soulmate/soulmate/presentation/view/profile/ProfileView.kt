package com.soulmate.soulmate.presentation.view.profile

import android.graphics.Bitmap
import com.arellomobile.mvp.MvpView

interface ProfileView : MvpView {
    fun setUsername(name: String)
    fun showImage(bitmap: Bitmap)
}
