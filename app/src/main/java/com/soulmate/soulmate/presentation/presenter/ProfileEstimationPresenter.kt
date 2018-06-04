package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.IProfileEstimationView

@InjectViewState
class ProfileEstimationPresenter : BasePresenter<IProfileEstimationView>(App.globalkodein.lazy) {

}
