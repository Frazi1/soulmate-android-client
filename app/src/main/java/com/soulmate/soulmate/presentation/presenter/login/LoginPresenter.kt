package com.soulmate.soulmate.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.*
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.R
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.HttpErrorCodes
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.authorization.AuthorizationToken
import com.soulmate.soulmate.presentation.view.login.LoginView
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.internal.http2.ErrorCode
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val authApi: AuthApi by instance()
    private val credentialsStore: CredentialsStore by instance()
    private val authorizationScheduler: AuthorizationScheduler by instance()

    init {
        injector.inject(App.globalkodein)
    }

    private val callback: Callback<AuthorizationToken> = object : Callback<AuthorizationToken> {
        override fun onFailure(call: Call<AuthorizationToken>?, t: Throwable?) {
            viewState.showToast(t.toString())
        }

        override fun onResponse(call: Call<AuthorizationToken>, response: Response<AuthorizationToken>) {
            if (response.isSuccessful) {
                val token: AuthorizationToken = response.body()!!
                credentialsStore.initializeWithToken(token)
                authorizationScheduler.startAuthorizationTask(AuthorizationScheduler.REFRESH_TOKEN_PERIOD)
                viewState.openProfileActivity()
            } else
                viewState.showToast("Invalid login or password")
        }
    }

    fun attemptLogin(username: String, password: String) {
        credentialsStore.initializeWithCredentials(username, password)
        val clientBasicAuthToken = CredentialsStore.getClientBasicAuthorizationToken()
//        val getTokenCall: Call<AuthorizationToken> =
//                authApi.getToken(username, password, clientBasicAuthToken)

//        getTokenCall.enqueue(callback)

        authApi.getTokenRx(username, password, clientBasicAuthToken)
//                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    credentialsStore.initializeWithToken(it)
                    authorizationScheduler.startAuthorizationTask(AuthorizationScheduler.REFRESH_TOKEN_PERIOD)
                    viewState.openProfileActivity()
                }, { it ->
                    if (it is HttpException) {
                        if (it.code() == HttpErrorCodes.NOT_AUTHORIZED.code){
                            viewState.showToast(App.instance.applicationContext.resources.getString(R.string.invalid_credentials))
                        }
                    }
                })
    }
}
