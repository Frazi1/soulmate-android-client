package com.soulmate.soulmate.authorization

import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.repositories.AuthRepository
import java.util.*

class AuthorizationScheduler(private val credentialsStore: CredentialsStore,
                             private val authApi: AuthApi,
                             private val authRepository: AuthRepository,
                             private val errorHandler: IErrorHandler) {
    companion object {
        private const val REFRESH_TOKEN_PERIOD_SECONDS: Int = 100
        const val REFRESH_TOKEN_PERIOD: Long = (1000 * REFRESH_TOKEN_PERIOD_SECONDS).toLong()
    }

    private var authTimer: Timer? = null

    fun startAuthorizationTask(delay: Long = 0) {
        if (!credentialsStore.isTokenInitialized) return
        authTimer = Timer()
        val task = object : TimerTask() {
            override fun run() {
                if (credentialsStore.isCredentialsInitialized) {
                    val newToken = authApi.refreshToken(
                            credentialsStore.authorizationToken.refreshToken,
                            CredentialsStore.getClientBasicAuthorizationToken())
                            .execute()
                            .body()!!
                    credentialsStore.initializeWithToken(newToken)
                } else {
                    authRepository.authorize(credentialsStore.username, credentialsStore.password, CredentialsStore.getClientBasicAuthorizationToken())
                            .subscribe({
                                credentialsStore.initializeWithToken(it)
                            }, { errorHandler.handle(it) })
                }
            }
        }
        authTimer!!.schedule(task, delay, REFRESH_TOKEN_PERIOD)
    }

    fun stopAuthorizationTask() {
        authTimer?.cancel()
    }
}