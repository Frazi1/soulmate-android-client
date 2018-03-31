package com.soulmate.soulmate.presentation.presenter.profile

import android.graphics.BitmapFactory
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.presenter.BaseSoulmatePresenter
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import dtos.ProfileImageDto
import dtos.UserAccountDto
import io.reactivex.android.schedulers.AndroidSchedulers
import java.io.InputStream

@InjectViewState
class ProfilePresenter : BaseSoulmatePresenter<ProfileView>(App.globalkodein.lazy) {
    private val userRepository: UserRepository by instance()
    private val imageRepository: ImageRepository by instance()
    private var userAccount: UserAccountDto? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadData()
    }

    private fun loadData() {
        viewState.setSpinnerVisibility(true)
        userRepository.loadUserProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWithDefaultErrorHandler(onSuccess = {
                    userAccount = it
                    viewState.setUsername(it.firstName)
                    if (it.profileImages.any()) {
                        val mainImage: ProfileImageDto = it.profileImages.first { it.isMainImage }
                        val bitmap = BitmapFactory.decodeStream(mainImage.data?.inputStream())
                        viewState.showImage(bitmap)
                    }
                }, doFinally = {
                    viewState.setSpinnerVisibility(false)
                })
    }

    fun saveData(newUserName: String) {
        userAccount?.let {
            it.firstName = newUserName
            userRepository.updateUserProfile(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWithDefaultErrorHandler()

        }
    }

    fun addImage(inputStream: InputStream?) {
        val data = inputStream?.readBytes()
        val bitmap = BitmapFactory.decodeStream(data?.inputStream())
        viewState.showImage(bitmap)
        imageRepository.uploadImage(ProfileImageDto(1, data, "", true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWithDefaultErrorHandler ({
                    viewState.showToast("Image upload finished")
                })
    }
}
