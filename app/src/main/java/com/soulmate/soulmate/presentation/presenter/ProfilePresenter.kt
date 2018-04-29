package com.soulmate.soulmate.presentation.presenter

import android.content.ContentResolver
import android.content.res.AssetFileDescriptor
import android.graphics.BitmapFactory
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.configuration.CredentialsStore
import com.soulmate.soulmate.presentation.view.IProfileView
import com.soulmate.soulmate.repositories.EstimationRepository
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import dtos.GenderType
import dtos.ProfileImageDto
import dtos.UserAccountDto
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ProfilePresenter : BasePresenter<IProfileView>(App.globalkodein.lazy) {
    private val userRepository: UserRepository by instance()
    private val imageRepository: ImageRepository by instance()
    private val estimationRepository: EstimationRepository by instance()
    private val contentResolver: ContentResolver by instance()
    private val credentialsStore: CredentialsStore by instance()

    private var userAccount: UserAccountDto? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onLoad()
    }

    private fun onLoad() {
        viewState.onLoading()
        userRepository.loadUserProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally({ viewState.onFinishedLoading() })
                .subscribe({
                    userAccount = it
                    viewState.showProfile(it)
                    if (it.profileImages.any()) {
                        val mainImage: ProfileImageDto = it.profileImages.first { it.isMainImage }
                        val bitmap = BitmapFactory.decodeStream(mainImage.data?.inputStream())
                        viewState.showImage(bitmap)
                    }
                }, defaultErrorHandler::handle)
    }

    fun saveProfile(newUserName: String,
                    gender: GenderType,
                    personalStory: String) {
        userAccount?.let {
            it.firstName = newUserName
            it.gender = gender
            it.personalStory = personalStory
            userRepository.updateUserProfile(it)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe({
                        viewState.showToast("Updated")
                    }, {
                        defaultErrorHandler.handle(it)
                    })

        }
    }

    fun addImage(uri: Uri) {
        val fileDescriptor: AssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r")
        val byteArray: ByteArray = fileDescriptor.createInputStream().readBytes()
//        val bitmap = BitmapFactory.decodeStream(data?.inputStream())
//        viewState.showImage(uri)
        imageRepository.uploadImage(ProfileImageDto(1, byteArray, "", true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    viewState.showToast("Image upload finished")
                }, { defaultErrorHandler.handle(it) })
    }

    fun logout() {
        credentialsStore.clear()
        viewState.openLoginActivity()
    }

    fun resetAllEstimations() {
        estimationRepository.resetAllEstimations()
                .createSubscription({}, defaultErrorHandler::handle)
    }
}
