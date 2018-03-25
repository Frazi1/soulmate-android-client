package com.soulmate.soulmate.presentation.presenter.profile

import android.graphics.BitmapFactory
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.App
import com.soulmate.soulmate.api.ProfileApi
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import dtos.ProfileImageDto
import dtos.UserAccountDto
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.HttpException
import retrofit2.Response
import java.io.InputStream

@InjectViewState
class ProfilePresenter : MvpPresenter<ProfileView>(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val profileApi: ProfileApi by instance()
    private val userRepository: UserRepository by instance()
    private val imageRepository: ImageRepository by instance()
    private var userAccount: UserAccountDto? = null

    init {
        inject(App.globalkodein)
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {
        viewState.setSpinnerVisibility(true)
        userRepository.loadUserProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    userAccount = it
                    viewState.setUsername(it.firstName)
                    if (it.profileImages.any()) {
                        val mainImage: ProfileImageDto = it.profileImages.first { it.isMainImage }
                        val bitmap = BitmapFactory.decodeStream(mainImage.data?.inputStream())
                        viewState.showImage(bitmap)
                    }
                    viewState.setSpinnerVisibility(false)
                }, {
                    if (it is HttpException)
                        viewState.showToast(it.response().errorBody()?.string()!!)
                    viewState.showToast(it.printStackTrace().toString())
                })
    }

    fun saveData(newUserName: String) {
        userAccount?.let {
            it.firstName = newUserName
            profileApi.updateUserProfile(it).enqueue(object : Callback<ResponseBody> {
                override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                    if (t != null) {
                        val a = t;
                    }
                }

                override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>) {
                    if (response.isSuccessful) {

                    }
                }
            })
        }
    }

    fun addImage(inputStream: InputStream?) {
        val data = inputStream?.readBytes()
        val bitmap = BitmapFactory.decodeStream(data?.inputStream())
        viewState.showImage(bitmap)
        imageRepository.uploadImage(ProfileImageDto(1, data, "", true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                }, {
                    if (it is HttpException) {
                        viewState.showToast(it.message())
                    } else {
                        viewState.showToast(it.printStackTrace().toString())
                    }
                })
    }
}
