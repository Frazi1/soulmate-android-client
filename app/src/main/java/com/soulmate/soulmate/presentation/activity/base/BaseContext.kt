package com.soulmate.soulmate.presentation.activity.base

import android.content.Context
import android.widget.Toast
import com.soulmate.soulmate.presentation.view.base.IBaseContext


class BaseContext(private val context: Context): IBaseContext {
    override fun showToast(text: String, duration: Int) {
        Toast.makeText(context, text, duration).show()
    }

    override fun showToast(text: String) {
        Toast.makeText(context, text, Toast.LENGTH_SHORT).show()
    }

}