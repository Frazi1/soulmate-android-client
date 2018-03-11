package com.soulmate.soulmate.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.ViewModel
import com.soulmate.dtos.UserAccountDto
import javax.inject.Inject

//class UserAccountViewModel : ViewModel() {
//    private var userId: String = ""
//        get set
//
//    lateinit var user: LiveData<UserAccountDto>
//        get set
//
//    @Inject
//    lateinit var userRepo: UserAccountRepository get set
//
//    fun init() {
//        user = userRepo.getUser()
//    }
//}