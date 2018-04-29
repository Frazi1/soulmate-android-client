package com.soulmate.soulmate.presentation.activity.base

import android.content.Context
import com.arellomobile.mvp.MvpAppCompatFragment
import com.soulmate.soulmate.presentation.view.base.IBaseMvpView


abstract class BaseFragment : MvpAppCompatFragment(), IBaseMvpView {
    private lateinit var baseContext: BaseContext

    private fun init(context: Context) {
        baseContext = BaseContext(context)
    }

    override fun showToast(text: String, duration: Int) {
        baseContext.showToast(text, duration)
    }

    override fun showToast(text: String) {
        baseContext.showToast(text)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context != null)
            init(context)
    }
}