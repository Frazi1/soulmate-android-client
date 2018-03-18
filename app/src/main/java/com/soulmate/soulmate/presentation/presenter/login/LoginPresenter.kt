package com.soulmate.soulmate.presentation.presenter.login

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.authorization.AuthorizationToken
import com.soulmate.soulmate.presentation.view.login.LoginView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

@InjectViewState
class LoginPresenter : MvpPresenter<LoginView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val authApi: Retrofit by instance()
    private val credentialsStore: CredentialsStore by instance()

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
                viewState.openMainActivity()
            } else
                viewState.showToast("Invalid login or password")
        }
    }

    fun attemptLogin(username: String, password: String) {
        val api = authApi.create(AuthApi::class.java)
        val basicAuthToken = CredentialsStore.getBasicAuthorizationToken()
        val getTokenCall: Call<AuthorizationToken> =
                api.getToken("password", username, password, basicAuthToken)

        getTokenCall.enqueue(callback)
    }
}
