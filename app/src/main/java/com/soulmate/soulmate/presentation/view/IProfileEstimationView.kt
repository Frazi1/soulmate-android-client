package com.soulmate.soulmate.presentation.view

import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.presentation.view.base.IBaseMvpView
import com.soulmate.soulmate.presentation.view.base.ILoader

interface IProfileEstimationView : IBaseMvpView, ILoader {
    fun displayUserAccount(profileEstimationDto: UserAccountDto?)
}
