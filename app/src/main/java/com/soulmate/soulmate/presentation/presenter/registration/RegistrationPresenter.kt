package com.soulmate.soulmate.presentation.presenter.registration

import android.app.Application
import android.content.Context
import android.content.res.Resources
import android.support.v4.content.res.ResourcesCompat
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.view.registration.RegistrationView
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import okhttp3.ResponseBody
import okhttp3.internal.http.RealResponseBody
import validation.ValidationResponse

@InjectViewState
class RegistrationPresenter() : MvpPresenter<RegistrationView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    init {
        inject(App.globalkodein)
    }

//    private val context: Application by instance()

    private val resources: Resources by instance()
    private val authRepository: AuthRepository by instance()
    private val validationResponseHandler: IValidationResponseHandler by instance()
    private val mapper: ObjectMapper by instance()

    fun registerUser(email: String, password: String) {
        authRepository.registerUser(email, password)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showToast(resources.getString(R.string.successful_registration))
                    viewState.openLoginActivity()
                }, {
                    if (it is HttpException) {
                        //                        viewState.showToast(resources.getString(R.string.email_already_taken))
//                        viewState.showToast(it.response().errorBody()?.string().toString(), 20)
                        if(it.code() == 422) {
                            val body = it.response().errorBody()
                            val v = mapper.readValue(body?.byteStream(), ValidationResponse::class.java)
                            viewState.showToast(validationResponseHandler.getValidatationMessage(
                                    v))
                        }
                        else {
                            viewState.showToast(it.response().errorBody()?.string()!!)
                        }
                    }
                })
    }
}
