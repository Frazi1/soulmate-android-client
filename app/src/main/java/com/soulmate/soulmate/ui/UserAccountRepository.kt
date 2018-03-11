package com.soulmate.soulmate.ui

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.api.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import javax.inject.Inject
import javax.inject.Singleton

//@Singleton
//class UserAccountRepository {
//
//    @Inject
//    lateinit var api: Retrofit
//    fun getUser() : LiveData<UserAccountDto> {
//        val data = MutableLiveData<UserAccountDto>()
//        api.create(TestApi::class.java).getAllUsers().enqueue(object : Callback<Iterable<UserAccountDto>> {
//            override fun onResponse(call: Call<Iterable<UserAccountDto>>?, response: Response<Iterable<UserAccountDto>>?) {
//                data.value = response?.body()?.first()
//            }
//
//            override fun onFailure(call: Call<Iterable<UserAccountDto>>?, t: Throwable?) {
//                TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
//            }
//        })
//        return data
//    }
//
//}