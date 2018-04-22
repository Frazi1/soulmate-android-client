package com.soulmate.soulmate.presentation.presenter

import android.content.res.Resources
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.R
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.presentation.view.RegistrationView
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class RegistrationPresenter() : BaseSoulmatePresenter<RegistrationView>(App.globalkodein.lazy) {
    private val resources: Resources by instance()
    private val authRepository: AuthRepository by instance()
    private val errorHandler: IErrorHandler by instance()
    private val credentialsStore: CredentialsStore by instance()

    fun registerUser(email: String, password: String) {
        credentialsStore.clear()
        authRepository.registerUser(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe ({
                    viewState.showToast(resources.getString(R.string.successful_registration))
                    viewState.openLoginActivity()
                }, errorHandler::handle)

    }
}
