package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.shared.Estimation
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.App
import com.soulmate.soulmate.configuration.interfaces.ISearchPreferencesManager
import com.soulmate.soulmate.interaction.helpers.PicassoWrapper
import com.soulmate.soulmate.presentation.view.IProfileEstimationView
import com.soulmate.soulmate.repositories.EstimationRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ProfileEstimationPresenter : BasePresenter<IProfileEstimationView>(App.globalkodein.lazy) {

    private val estimationRepository: EstimationRepository by instance()
    private val picassoWrapper: PicassoWrapper by instance()
    private val searchPreferenceManager: ISearchPreferencesManager by instance()

    private val currentUserAccount: UserAccountDto?
        get() = getAccount(currentUserAccountIndex)

    private val nextUserAccount: UserAccountDto?
        get() = getAccount(currentUserAccountIndex + 1)


    private var currentUserAccountIndex: Int = 0
    private var userAccountList: List<UserAccountDto> = arrayListOf()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        viewState.onLoading()
        loadEstimationProfiles()
    }

    fun loadEstimationProfiles() {
        estimationRepository.getUsersForEstimation(getFiltrationOptions())
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally { viewState.onFinishedLoading() }
                .createSubscription({
                    currentUserAccountIndex = 0
                    userAccountList = it.toList()
                    viewState.displayUserAccount(currentUserAccount)
                    preloadNextUserImages()
                })
    }

    private fun getAccount(index: Int): UserAccountDto? {
        if (userAccountList.count() - 1 < index)
            return null
        return userAccountList[index]
    }

    private fun preloadNextUserImages() {
        nextUserAccount
                ?.profileImages
                ?.forEach { picassoWrapper.fetchImage(it.imageId) }
    }

    fun estimateUser(estimation: Estimation) {
        currentUserAccount?.let { userAccount ->
            estimationRepository.estimateUser(userAccount.id, estimation)
                    .observeOn(AndroidSchedulers.mainThread())
                    .createSubscription({})
        }
        currentUserAccountIndex++
        viewState.displayUserAccount(currentUserAccount)
        preloadNextUserImages()
    }

    private fun getFiltrationOptions(): Map<String, String> {
        with(searchPreferenceManager) {
            return mapOf(
                    Pair("ageFrom", minAge.toString()),
                    Pair("ageTo", maxAge.toString()))
        }
    }
}