package com.soulmate.soulmate.presentation.activity.base

import android.view.View
import com.soulmate.soulmate.presentation.view.ILoader


abstract class LoaderFragment : BaseFragment(), ILoader {

    abstract var loaderView: View

    override fun onLoading() {
        loaderView.visibility = View.VISIBLE
    }

    override fun onFinishedLoading() {
        loaderView.visibility = View.GONE
    }
}