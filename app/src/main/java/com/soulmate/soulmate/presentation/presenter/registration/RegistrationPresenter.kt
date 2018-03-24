package com.soulmate.soulmate.presentation.presenter.registration

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.registration.RegistrationView
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.R

@InjectViewState
class RegistrationPresenter() : MvpPresenter<RegistrationView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    init {
        inject(App.globalkodein)
    }

//    private val context: Application by instance()

    private val resources: Resources by instance()
    private val authRepository: AuthRepository by instance()

    fun registerUser(email: String, password: String) {
        authRepository.registerUser(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showToast(resources.getString(R.string.successful_registration))
                    viewState.openLoginActivity()
                }, {
                    if (it is HttpException)
                        viewState.showToast(resources.getString(R.string.email_already_taken))
                })
    }
}
