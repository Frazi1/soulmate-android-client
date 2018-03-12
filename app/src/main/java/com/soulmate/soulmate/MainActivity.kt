package com.soulmate.soulmate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.ActivityInjector
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.github.salomonbrys.kodein.bindings.Scope
import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.api.TestApi
import com.soulmate.soulmate.ui.NavigationHelper
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), AppCompatActivityInjector {
    override val injector: KodeinInjector = KodeinInjector()
    //    lateinit var navigationHelper: Provider<NavigationHelper>
    val retrofit: Retrofit by instance()
    private val navigationHelper: NavigationHelper by instance()

    override fun provideOverridingModule() = Kodein.Module {
        bind<MainActivity>() with instance(this@MainActivity)
        bind<NavigationHelper>() with singleton { NavigationHelper(instance()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_main)
        navigationHelper.navigateToAuthorization()

//        retrofit.create(TestApi::class.java)?.getAllUsers()?.enqueue(object : Callback<Iterable<UserAccountDto>> {
//            override fun onFailure(call: Call<Iterable<UserAccountDto>>?, t: Throwable?) {
//                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
//            }
//
//            override fun onResponse(call: Call<Iterable<UserAccountDto>>?, response: Response<Iterable<UserAccountDto>>?) {
//                Toast.makeText(this@MainActivity, response?.body()?.joinToString { it.firstName }, Toast.LENGTH_SHORT).show()
//            }
//        })
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
