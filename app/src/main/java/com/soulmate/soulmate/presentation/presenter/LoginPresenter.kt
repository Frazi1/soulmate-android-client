package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.configuration.CredentialsStore
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.interaction.authorization.OAuthTokenDto
import com.soulmate.soulmate.interaction.authorization.AuthorizationScheduler
import com.soulmate.soulmate.presentation.view.ILoginView
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class LoginPresenter : BasePresenter<ILoginView>(App.globalkodein.lazy) {
    private val authRepository: AuthRepository by instance()
    private val credentialsStore: CredentialsStore by instance()
    private val authorizationScheduler: AuthorizationScheduler by instance()
    private val errorHandler: IErrorHandler by instance()

    override fun onFirstViewAttach() {
        if (credentialsStore.authorizationToken.isExpired)
            credentialsStore.clear()
        viewState.setUsername(credentialsStore.username)
        viewState.setPassword(credentialsStore.password)
    }

    fun attemptAutoLogin(): Boolean {
        if (credentialsStore.isCredentialsInitialized)
            attemptLogin(credentialsStore.username, credentialsStore.password, false)
        return credentialsStore.isCredentialsInitialized
    }

    fun attemptLogin(username: String, password: String, reinitialize: Boolean = true) {
        if (reinitialize)
            credentialsStore.initializeWithCredentials(username, password)
        val clientBasicAuthToken = CredentialsStore.getClientBasicAuthorizationToken()


        authRepository.authorize(username, password, clientBasicAuthToken)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    credentialsStore.initializeWithToken(OAuthTokenDto.fromAccessToken(it))
//                    authorizationScheduler.startAuthorizationTask(AuthorizationScheduler.REFRESH_TOKEN_PERIOD)
                    viewState.openProfileActivity()
                }, errorHandler::handle)
    }
}
