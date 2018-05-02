package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.shared.Estimation
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.IProfileEstimationView
import com.soulmate.soulmate.repositories.EstimationRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ProfileEstimationPresenter : BasePresenter<IProfileEstimationView>(App.globalkodein.lazy) {

    private val estimationRepository: EstimationRepository by instance()

    private val currentUserAccount: UserAccountDto?
        get() {
            if (userAccountList.count() - 1 < currentUserAccountIndex)
                return null
            return userAccountList[currentUserAccountIndex]
        }

    private var currentUserAccountIndex: Int = 0
    private var userAccountList: List<UserAccountDto> = arrayListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onLoading()
        loadEstimationProfiles()
    }

    fun loadEstimationProfiles() {
        estimationRepository.getUsersForEstimation()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.onFinishedLoading() }
                .createSubscription({
                    currentUserAccountIndex = 0
                    userAccountList = it.toList()
                    viewState.displayUserAccount(currentUserAccount)
                })
    }

    fun estimateUser(estimation: Estimation) {
        currentUserAccount?.let { userAccount ->
            estimationRepository.estimateUser(userAccount.id, estimation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .createSubscription({})
        }
        currentUserAccountIndex++
        viewState.displayUserAccount(currentUserAccount)
    }
}