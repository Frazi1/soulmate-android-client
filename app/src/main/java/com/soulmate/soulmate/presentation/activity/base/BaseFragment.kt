package com.soulmate.soulmate.presentation.activity.base

import android.content.Context
import com.arellomobile.mvp.MvpAppCompatFragment
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.base.IBaseMvpView


abstract class BaseFragment : MvpAppCompatFragment(), IBaseMvpView, LazyKodeinAware {
    private lateinit var baseContext: BaseContext

    override val kodein: LazyKodein = App.globalkodein.lazy

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