package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.ProfileEstimationView

@InjectViewState
class ProfileEstimationPresenter : BaseSoulmatePresenter<ProfileEstimationView>(App.globalkodein.lazy) {

}
