package com.soulmate.soulmate

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.widget.Button
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.AppCompatActivityInjector
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.authorization.AuthorizationToken
import com.soulmate.soulmate.ui.NavigationHelper
import com.soulmate.soulmate.ui.activity.login.LoginActivity
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity(), AppCompatActivityInjector {
    override val injector: KodeinInjector = KodeinInjector()

    private val authApi: AuthApi by instance()
    private val credentialsStore: CredentialsStore by instance()

    private lateinit var buttonGetData: Button;
    private lateinit var buttonRefresh: Button;

    override fun provideOverridingModule() = Kodein.Module {
        bind<MainActivity>() with instance(this@MainActivity)
        bind<NavigationHelper>() with singleton { NavigationHelper(instance()) }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initializeInjector()
        setContentView(R.layout.activity_main)

        if (!credentialsStore.isTokenInitialized) {
            val intent = LoginActivity.getIntent(this)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            startActivity(intent)
        }

        buttonGetData = findViewById(R.id.test)
        buttonRefresh = findViewById(R.id.button_refresh_token)
//        navigationHelper.navigateToAuthorization()
//        buttonGetData.setOnClickListener {
//            authApi.getAllUsers().enqueue(object : Callback<Iterable<UserAccountDto>> {
//                override fun onFailure(call: Call<Iterable<UserAccountDto>>?, t: Throwable?) {
//                    Toast.makeText(this@MainActivity, t.toString(), Toast.LENGTH_SHORT).show()
//                }
//
//                override fun onResponse(call: Call<Iterable<UserAccountDto>>?, response: Response<Iterable<UserAccountDto>>?) {
//                    Toast.makeText(this@MainActivity, response?.body()?.joinToString { it.firstName }, Toast.LENGTH_SHORT).show()
//                }
//            })
//        }

        buttonRefresh.setOnClickListener {
            authApi
                    .refreshToken(
                    credentialsStore.authorizationToken.refreshToken,
                    CredentialsStore.getClientBasicAuthorizationToken()).enqueue(object : Callback<AuthorizationToken> {
                override fun onResponse(call: Call<AuthorizationToken>?, response: Response<AuthorizationToken>) {
                    if (response.isSuccessful)
                        credentialsStore.initializeWithToken(response.body()!!)
                }

                override fun onFailure(call: Call<AuthorizationToken>?, t: Throwable?) {
                    TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        destroyInjector()
    }
}
