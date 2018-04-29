package com.soulmate.soulmate.presentation.view

import com.soulmate.soulmate.presentation.view.base.IBaseMvpView
import com.soulmate.soulmate.presentation.view.base.ILoader
import dtos.ProfileEstimationDto

interface IProfileEstimationView : IBaseMvpView, ILoader {
    fun displayProfileEstimation(profileEstimationDto: ProfileEstimationDto?)
}
