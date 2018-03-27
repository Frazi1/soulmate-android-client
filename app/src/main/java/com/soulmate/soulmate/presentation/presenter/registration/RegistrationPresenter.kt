package com.soulmate.soulmate.presentation.presenter.registration

import android.content.res.Resources
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.App
import com.soulmate.soulmate.R
import com.soulmate.soulmate.api.errors.CallbackWrapper
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.presentation.view.registration.RegistrationView
import com.soulmate.soulmate.repositories.AuthRepository
import okhttp3.ResponseBody

@InjectViewState
class RegistrationPresenter() : MvpPresenter<RegistrationView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    init {
        inject(App.globalkodein)
    }

//    private val context: Application by instance()

    private val resources: Resources by instance()
    private val authRepository: AuthRepository by instance()
    private val toastErrorHandler: IErrorHandler by instance()

    fun registerUser(email: String, password: String) {
        authRepository.registerUser(email, password)
                .subscribeWith(object : CallbackWrapper<ResponseBody>(App.globalkodein) {
                    override fun onSuccess(t: ResponseBody) {
                        viewState.showToast(resources.getString(R.string.successful_registration))
                        viewState.openLoginActivity()
                    }
                })
    }
}
