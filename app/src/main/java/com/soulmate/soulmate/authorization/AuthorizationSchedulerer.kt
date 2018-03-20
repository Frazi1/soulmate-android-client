package com.soulmate.soulmate.authorization

import com.github.salomonbrys.kodein.KodeinInjector
import com.soulmate.soulmate.App
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import java.util.*

class AuthorizationSchedulerer(private val credentialsStore: CredentialsStore,
                               private val authApi: AuthApi) {
//    override val injector: KodeinInjector = KodeinInjector()

    private val authTimer: Timer = Timer()

//    init {
//        inject(App.globalkodein)
//    }

    fun startAuthorizationTask() {
        val task = object : TimerTask() {
            override fun run() {
                val newToken = authApi.refreshToken(
                        credentialsStore.authorizationToken.refreshToken,
                        CredentialsStore.getClientBasicAuthorizationToken())
                        .execute()
                        .body()!!
                credentialsStore.initializeWithToken(newToken)
            }
        }
        authTimer.schedule(task, 5000, 5000)
    }

    fun stopAuthorizationTask() {
        authTimer.cancel()
    }

}