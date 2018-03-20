package com.soulmate.soulmate.presentation.presenter.profile

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.App
import com.soulmate.soulmate.api.ProfileApi
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@InjectViewState
class ProfilePresenter : MvpPresenter<ProfileView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val profileApi: ProfileApi by instance()
    private var userAccount: UserAccountDto? = null

    init {
        inject(App.globalkodein)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {
        profileApi.getUserProfile().enqueue(object: Callback<UserAccountDto>{
            override fun onFailure(call: Call<UserAccountDto>?, t: Throwable?) {
                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
            }

            override fun onResponse(call: Call<UserAccountDto>, response: Response<UserAccountDto>) {
                if(response.isSuccessful)
                    userAccount = response.body()
                    userAccount?.let {
                        viewState.setUsername(it.firstName)
                    }
            }
        })
    }

    fun saveData(newUserName: String) {
        userAccount?.let {
            it.firstName = newUserName
            profileApi.updateUserProfile(it).enqueue(object: Callback<ResponseBody>{
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    if(t != null){
                        val a = t;
                    }
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                    if(response.isSuccessful)
                    {

                    }
                }
            })
        }
    }
}
