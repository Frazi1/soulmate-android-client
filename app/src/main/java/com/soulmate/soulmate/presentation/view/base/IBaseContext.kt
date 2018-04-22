package com.soulmate.soulmate.presentation.view.base

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface IBaseContext {
    fun showToast(text: String, duration: Int)
    fun showToast(text: String)
}