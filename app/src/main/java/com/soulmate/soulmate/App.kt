package com.soulmate.soulmate

import android.app.Application
import android.content.Context
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.ProfileApi
import com.soulmate.soulmate.authorization.AuthorizationInterceptor
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.configuration.AppLifeCycleObserver
import com.soulmate.soulmate.configuration.IAppLifeCycle
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import com.soulmate.soulmate.presentation.validation.ValidationResponseHandler
import com.soulmate.soulmate.repositories.AuthRepository
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory

class App : Application(), KodeinAware, IAppLifeCycle {
    companion object {
        lateinit var instance: App
        lateinit var globalkodein: Kodein
    }

    private val authorizationScheduler: AuthorizationScheduler by lazy { instance<AuthorizationScheduler>() }
    private val appLifeCycleObserver: AppLifeCycleObserver = AppLifeCycleObserver(this)

    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@App))

        bind<ObjectMapper>() with singleton { buildObjectMapper()}

        bind<CredentialsStore>() with singleton { CredentialsStore() }
//        bind<Context>() with instance(this@App)
        bind<Retrofit>() with singleton { buildRetrofit(instance()) }
        bind<AuthorizationScheduler>() with singleton { AuthorizationScheduler(instance(), instance()) }

        bind<IValidationResponseHandler>() with singleton { ValidationResponseHandler() }

        //API
        bind<AuthApi>() with singleton { kodein.instance<Retrofit>().create(AuthApi::class.java) }
        bind<ProfileApi>() with singleton { kodein.instance<Retrofit>().create(ProfileApi::class.java) }

        //Repo
        bind<AuthRepository>() with singleton { AuthRepository(instance()) }
    }

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.addInterceptor(AuthorizationInterceptor(kodein.instance()))
//        builder.authenticator(TokenAuthenticator(kodein))
        return builder.build()
    }

    private fun buildRetrofit(objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
                .baseUrl("http://192.168.0.100:8080")
                .addConverterFactory(JacksonConverterFactory.create(objectMapper))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.createWithScheduler(Schedulers.io()))
                .client(httpClient())
                .build()
    }

    private fun buildObjectMapper(): ObjectMapper =
            ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    override fun onCreate() {
        super.onCreate()
        instance = this
        globalkodein = kodein
        registerActivityLifecycleCallbacks(appLifeCycleObserver)
    }

    override fun onTrimMemory(level: Int) {
        super.onTrimMemory(level)
        appLifeCycleObserver.onTrimMemory(level)
    }

    override fun onGoToForeground() {
        authorizationScheduler.startAuthorizationTask()
    }

    override fun onGoToBackground() {
        authorizationScheduler.stopAuthorizationTask()
    }
}