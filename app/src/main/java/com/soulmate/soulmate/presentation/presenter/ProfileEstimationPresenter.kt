package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.IProfileEstimationView
import com.soulmate.soulmate.repositories.EstimationRepository
import dtos.ProfileEstimationDto
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ProfileEstimationPresenter : BasePresenter<IProfileEstimationView>(App.globalkodein.lazy) {

    private val estimationRepository: EstimationRepository by instance()

    private val currentProfileEstimation: ProfileEstimationDto?
        get() {
            if (profileEstimationList.count() - 1 < currentProfileEstimationIndex)
                return null
            return profileEstimationList[currentProfileEstimationIndex]
        }

    private var currentProfileEstimationIndex: Int = 0
    private var profileEstimationList: List<ProfileEstimationDto> = arrayListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onLoading()
        loadEstimationProfiles()
    }

    fun loadEstimationProfiles() {
        estimationRepository.getProfileEstimations()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.onFinishedLoading() }
                .createSubscription({
                    profileEstimationList = it.toList()
                    viewState.displayProfileEstimation(currentProfileEstimation)
                })
    }

    fun likeProfile() {
        currentProfileEstimation?.let { profileEstimation ->
            estimationRepository.likeProfile(profileEstimation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .createSubscription({})
        }
        currentProfileEstimationIndex++
        viewState.displayProfileEstimation(currentProfileEstimation)
    }
}