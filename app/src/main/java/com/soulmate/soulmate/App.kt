package com.soulmate.soulmate

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.soulmate.soulmate.api.AuthApi
import com.soulmate.soulmate.api.ImageApi
import com.soulmate.soulmate.api.ProfileApi
import com.soulmate.soulmate.api.errors.*
import com.soulmate.soulmate.authorization.AuthorizationInterceptor
import com.soulmate.soulmate.authorization.AuthorizationScheduler
import com.soulmate.soulmate.authorization.TokenAuthenticator
import com.soulmate.soulmate.configuration.*
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import com.soulmate.soulmate.presentation.validation.ValidationResponseHandler
import com.soulmate.soulmate.repositories.AuthRepository
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import io.reactivex.schedulers.Schedulers
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.jackson.JacksonConverterFactory
import java.util.concurrent.TimeUnit

class App : Application(), KodeinAware, IAppLifeCycle {
    companion object {
        lateinit var instance: App
        lateinit var globalkodein: Kodein
    }

    private val authorizationScheduler: AuthorizationScheduler by lazy { instance<AuthorizationScheduler>() }
    private val appLifeCycleObserver: AppLifeCycleObserver = AppLifeCycleObserver(this)

    @Suppress("RemoveExplicitTypeArguments")
    override val kodein by Kodein.lazy {
        import(autoAndroidModule(this@App))

        bind<ObjectMapper>() with singleton { buildObjectMapper() }

        bind<IConnectionPreferenceManager>() with singleton { ConnectionPreferenceManager(this@App, instance<Resources>()) }
        bind<CredentialsStore>() with singleton { CredentialsStore(getSharedPreferences("settings", Context.MODE_PRIVATE)) }
//        bind<Retrofit>() with singleton { buildRetrofit(instance()) }
        bind<RetrofitProvider>() with singleton { RetrofitProvider(instance<ObjectMapper>(), instance<CredentialsStore>(), instance<IConnectionPreferenceManager>()) }
        bind<Retrofit>() with provider { instance<RetrofitProvider>().provide() }
        bind<AuthorizationScheduler>() with singleton {
            AuthorizationScheduler(
                    instance<CredentialsStore>(),
                    instance<AuthApi>(),
                    instance<AuthRepository>(),
                    instance<IErrorHandler>())
        }
        bind<ScheduleProvider>() with singleton { ScheduleProvider() }


        //API
        bind<AuthApi>() with singleton { instance<Retrofit>().create(AuthApi::class.java) }
        bind<ProfileApi>() with singleton { instance<Retrofit>().create(ProfileApi::class.java) }
        bind<ImageApi>() with singleton { instance<Retrofit>().create(ImageApi::class.java) }

        //Repo
        bind<AuthRepository>() with singleton {
            AuthRepository(
                    instance<AuthApi>(),
                    instance<ScheduleProvider>(),
                    instance<IErrorHandler>()
            )
        }
        bind<ImageRepository>() with singleton {
            ImageRepository(
                    instance<ImageApi>(),
                    instance<ScheduleProvider>(),
                    instance<IErrorHandler>()
            )
        }
        bind<UserRepository>() with singleton {
            UserRepository(
                    instance<ProfileApi>(),
                    instance<ScheduleProvider>(),
                    instance<IErrorHandler>()
            )
        }

        //TODO: fix me
        bind<IErrorMessageExtractor>() with singleton {
            HttpErrorMessageExtractor(
                    instance<ObjectMapper>(),
                    instance<IValidationResponseHandler>(),
                    instance<Resources>()
            )
        }
        bind<IErrorHandler>() with singleton { ToastErrorMessageHandler(this@App, instance<IErrorMessageExtractor>()) }
        bind<IValidationResponseHandler>() with singleton { ValidationResponseHandler() }

    }

    private fun httpClient(): OkHttpClient {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(10, TimeUnit.SECONDS)
        builder.addInterceptor(AuthorizationInterceptor(instance<CredentialsStore>()))
//        builder.authenticator(TokenAuthenticator(kodein.lazy))
        return builder.build()
    }

    private fun buildRetrofit(objectMapper: ObjectMapper): Retrofit {
        return Retrofit.Builder()
//                .baseUrl("http://192.168.0.100:8080")
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
//        authorizationScheduler.startAuthorizationTask()
    }

    override fun onGoToBackground() {
//        authorizationScheduler.stopAuthorizationTask()
    }
}