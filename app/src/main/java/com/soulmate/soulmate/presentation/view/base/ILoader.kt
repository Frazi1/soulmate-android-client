package com.soulmate.soulmate.presentation.view.base

import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

@StateStrategyType(SkipStrategy::class)
interface ILoader {
    fun onLoading()
    fun onFinishedLoading()
}