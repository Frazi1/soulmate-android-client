package com.soulmate.soulmate.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.presentation.presenter.BaseSoulmatePresenter
import com.soulmate.soulmate.presentation.view.login.LoginView
import com.soulmate.soulmate.repositories.AuthRepository

@InjectViewState
class LoginPresenter : BaseSoulmatePresenter<LoginView>(App.globalkodein.lazy) {
    private val authRepository: AuthRepository by instance()
    private val credentialsStore: CredentialsStore by instance()
    private val authorizationScheduler: AuthorizationScheduler by instance()

    fun attemptLogin(username: String, password: String) {
        credentialsStore.initializeWithCredentials(username, password)
        val clientBasicAuthToken = CredentialsStore.getClientBasicAuthorizationToken()
        authRepository.authorize(username, password, clientBasicAuthToken)
                .subscribeWithDefaultErrorHandler ({
                    credentialsStore.initializeWithToken(it)
                    authorizationScheduler.startAuthorizationTask(AuthorizationScheduler.REFRESH_TOKEN_PERIOD)
                    viewState.openProfileActivity()
                })
    }
}
