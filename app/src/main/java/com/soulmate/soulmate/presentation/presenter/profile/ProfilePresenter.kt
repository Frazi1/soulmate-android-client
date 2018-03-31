package com.soulmate.soulmate.presentation.presenter.profile

import android.content.ContentResolver
import android.content.res.AssetFileDescriptor
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.ParcelFileDescriptor
import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.App
import com.soulmate.soulmate.presentation.presenter.BaseSoulmatePresenter
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import com.squareup.picasso.Picasso
import dtos.ProfileImageDto
import dtos.UserAccountDto
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ProfilePresenter : BaseSoulmatePresenter<ProfileView>(App.globalkodein.lazy) {
    private val userRepository: UserRepository by instance()
    private val imageRepository: ImageRepository by instance()
    private val contentResolver: ContentResolver by instance()
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

    fun addImage(uri: Uri) {
        val fileDescriptor: AssetFileDescriptor = contentResolver.openAssetFileDescriptor(uri, "r")
        val byteArray: ByteArray = fileDescriptor.createInputStream().readBytes()
//        val bitmap = BitmapFactory.decodeStream(data?.inputStream())
//        viewState.showImage(uri)
        imageRepository.uploadImage(ProfileImageDto(1, byteArray, "", true))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWithDefaultErrorHandler ({
                    viewState.showToast("Image upload finished")
                })
    }
}
