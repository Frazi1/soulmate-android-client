package com.soulmate.soulmate.presentation.activity.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.arellomobile.mvp.MvpAppCompatFragment
import com.soulmate.soulmate.presentation.view.IBaseSoulmateMvpView


abstract class BaseFragment : MvpAppCompatFragment(), IBaseSoulmateMvpView {
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