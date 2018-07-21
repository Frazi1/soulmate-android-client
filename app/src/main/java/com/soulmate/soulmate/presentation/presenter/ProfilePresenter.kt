package com.soulmate.soulmate.presentation.presenter

import android.content.ContentResolver
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.net.Uri
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.shared.GenderType
import com.soulmate.shared.dtos.UploadImageDto
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.App
import com.soulmate.soulmate.NotificationService
import com.soulmate.soulmate.configuration.CredentialsStore
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.presentation.view.IProfileView
import com.soulmate.soulmate.repositories.EstimationRepository
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ProfilePresenter : BasePresenter<IProfileView>(App.globalkodein.lazy) {
    private val userRepository: UserRepository by instance()
    private val imageRepository: ImageRepository by instance()
    private val estimationRepository: EstimationRepository by instance()
    private val contentResolver: ContentResolver by instance()
    private val credentialsStore: CredentialsStore by instance()
    private val userContextHolder: IUserContexHolder by instance()

    private var userAccount: UserAccountDto? = null

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        onLoad()
    }

    private fun onLoad() {
        viewState.onLoading()
        userRepository.getOwnProfile()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally({ viewState.onFinishedLoading() })
                .createSubscription({
                    userAccount = it
                    userContextHolder.user = userAccount
                    viewState.showProfile(it)
                })
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
                    .createSubscription({
                        viewState.showToast("Updated")
                    })
        }
    }

    fun addImage(uri: Uri) {
        val fileDescriptor: AssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r")
        val byteArray: ByteArray = fileDescriptor.createInputStream().readBytes()
        imageRepository.uploadImage(UploadImageDto(1, byteArray, "", true))
                .observeOn(AndroidSchedulers.mainThread())
                .createSubscription({
                    viewState.showToast("Image upload finished")
                    onLoad()
                })
    }

    fun logout() {
        credentialsStore.clear()
        App.instance.applicationContext.stopService(Intent(App.instance.applicationContext, NotificationService::class.java))
        viewState.openLoginActivity()
    }

    fun resetAllEstimations() {
        estimationRepository.resetAllEstimations()
                .createSubscription({}, defaultErrorHandler::handle)
    }
}
