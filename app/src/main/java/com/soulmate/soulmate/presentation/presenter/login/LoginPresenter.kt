package com.soulmate.soulmate.presentation.presenter.login

import android.content.Intent
import android.widget.Toast
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.MainActivity
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.presentation.view.login.LoginView
import okhttp3.ResponseBody
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

    fun attemptLogin(username: String, password: String) {
        val api = authApi.create(AuthApi::class.java)
        val checkCredentialsCall: Call<ResponseBody> =
            api.checkCredentials(CredentialsStore.getBasicAuthorizationToken(username, password))
        checkCredentialsCall.enqueue(object : Callback<ResponseBody?> {
            override fun onFailure(call: Call<ResponseBody?>?, t: Throwable?) {
            }

            override fun onResponse(call: Call<ResponseBody?>, response: Response<ResponseBody?>) {
                if (response.isSuccessful) {
                    val body = response.body()
                    credentialsStore.initialize(username, password)
                    App.instance.buildRetrofit()
                    viewState.openMainActivity()
                }
                else
                    viewState.showToast("Invalid login or password")
            }
        })
    }
}
