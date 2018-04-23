package com.soulmate.soulmate.interaction.authorization

import android.os.Looper
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route

class TokenAuthenticator(override val kodein: LazyKodein) : Authenticator, LazyKodeinAware {

    private val credentialsStore: CredentialsStore by instance()
    private val authRepository: AuthRepository by instance()
    private val errorHandler: IErrorHandler by instance()

    override fun authenticate(route: Route, response: Response): Request? {

        var gotValidToken = false
        if (credentialsStore.isTokenInitialized) {
            authRepository.refreshToken(credentialsStore.authorizationToken.refreshToken, CredentialsStore.getClientBasicAuthorizationToken())
                    .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
                    .subscribe({
                        credentialsStore.initializeWithToken(OAuthTokenDto.fromAccessToken(it))
                        gotValidToken = credentialsStore.isTokenInitialized
                    }, errorHandler::handle)
        } else if (!credentialsStore.isTokenInitialized && credentialsStore.isCredentialsInitialized) {
            authRepository.authorize(credentialsStore.username, credentialsStore.password, CredentialsStore.getClientBasicAuthorizationToken())
                    .observeOn(AndroidSchedulers.from(Looper.getMainLooper()))
                    .subscribe({
                        credentialsStore.initializeWithToken(OAuthTokenDto.fromAccessToken(it))
                        gotValidToken = credentialsStore.isTokenInitialized
                    }, errorHandler::handle)
        }
        return if (gotValidToken)
            response.request().newBuilder()
                    .addHeader("Authorization", "${credentialsStore.authorizationToken.tokenType} ${credentialsStore.authorizationToken.accessToken}")
                    .build()
        else
            null
    }
}