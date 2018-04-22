package com.soulmate.soulmate.presentation.view

import dtos.ProfileEstimationDto

interface IProfileEstimationView : IBaseMvpView, ILoader {
    fun displayProfileEstimation(profileEstimationDto: ProfileEstimationDto?)
}
