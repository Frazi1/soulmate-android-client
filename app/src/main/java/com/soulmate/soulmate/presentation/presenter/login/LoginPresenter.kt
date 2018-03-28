package com.soulmate.soulmate.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.R
import com.soulmate.soulmate.api.HttpErrorCodes
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.presentation.presenter.BaseSoulmatePresenter
import com.soulmate.soulmate.presentation.view.login.LoginView
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class LoginPresenter : BaseSoulmatePresenter<LoginView>(App.globalkodein.lazy) {
    private val authRepository: AuthRepository by instance()
    private val credentialsStore: CredentialsStore by instance()
    private val authorizationScheduler: AuthorizationScheduler by instance()

    fun attemptLogin(username: String, password: String) {
        credentialsStore.initializeWithCredentials(username, password)
        val clientBasicAuthToken = CredentialsStore.getClientBasicAuthorizationToken()
        authRepository.authorize(username, password, clientBasicAuthToken)
                .subscribeWithErrorHandler {
                    credentialsStore.initializeWithToken(it)
                    authorizationScheduler.startAuthorizationTask(AuthorizationScheduler.REFRESH_TOKEN_PERIOD)
                    viewState.openProfileActivity()
                }
    }
}
