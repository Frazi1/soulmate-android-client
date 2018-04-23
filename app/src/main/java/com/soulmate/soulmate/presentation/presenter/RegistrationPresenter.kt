package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.presentation.view.IRegistrationView
import com.soulmate.soulmate.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class RegistrationPresenter : BasePresenter<IRegistrationView>(App.globalkodein.lazy) {
    private val userRepository: UserRepository by instance()
    private val credentialsStore: CredentialsStore by instance()

    fun registerUser(email: String, password: String) {
        credentialsStore.clear()
        userRepository.registerUser(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .createSubscription ({
                    viewState.onSuccessfulRegistration()
                })
    }
}
