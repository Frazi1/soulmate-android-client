package com.soulmate.soulmate.ui.activity

import android.widget.Toast
import com.arellomobile.mvp.MvpAppCompatActivity
import com.soulmate.soulmate.presentation.view.ISoulmateBaseMvpView

abstract class BaseSoulmateActivity : MvpAppCompatActivity(), ISoulmateBaseMvpView {
    override fun showToast(text: String, duration: Int) {
        Toast.makeText(this, text, duration).show()
    }

    override fun showToast(text: String) {
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show()
    }
}