package com.soulmate.soulmate.presentation.view

import com.arellomobile.mvp.MvpView
import dtos.ProfileEstimationDto

interface IProfileEstimationView : MvpView {
    fun displayProfileEstimation(profileEstimationDto: ProfileEstimationDto)
}
