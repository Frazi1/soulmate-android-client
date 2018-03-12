package com.soulmate.soulmate

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.android.appKodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.dtos.UserAccountDto
import com.soulmate.soulmate.api.TestApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit

class MainActivity : AppCompatActivity(), KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()
    //    lateinit var navigationHelper: Provider<NavigationHelper>
    val retrofit: Retrofit by instance()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        inject(appKodein())
//        navigationHelper.get().navigateToAuthorization()
        setContentView(R.layout.activity_main)
        retrofit.create(TestApi::class.java)?.getAllUsers()?.enqueue(object : Callback<Iterable<UserAccountDto>> {
            override fun onFailure(call: Call<Iterable<UserAccountDto>>?, t: Throwable?) {
                Toast.makeText(this@MainActivity, "Error", Toast.LENGTH_SHORT).show()
            }

            override fun onResponse(call: Call<Iterable<UserAccountDto>>?, response: Response<Iterable<UserAccountDto>>?) {
                Toast.makeText(this@MainActivity, response?.body()?.joinToString { it.firstName }, Toast.LENGTH_SHORT).show()
            }
        })
    }
}
