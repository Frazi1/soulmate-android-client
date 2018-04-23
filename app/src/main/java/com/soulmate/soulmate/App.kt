package com.soulmate.soulmate

import android.app.Application
import android.content.Context
import android.content.res.Resources
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.*
import com.github.salomonbrys.kodein.android.autoAndroidModule
import com.soulmate.soulmate.interaction.api.AuthApi
import com.soulmate.soulmate.interaction.api.EstimationApi
import com.soulmate.soulmate.interaction.api.ImageApi
import com.soulmate.soulmate.interaction.api.UserApi
import com.soulmate.soulmate.api.errors.*
import com.soulmate.soulmate.interaction.authorization.AuthorizationScheduler
import com.soulmate.soulmate.configuration.*
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import com.soulmate.soulmate.presentation.validation.ValidationResponseHandler
import com.soulmate.soulmate.repositories.AuthRepository
import com.soulmate.soulmate.repositories.EstimationRepository
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import retrofit2.Retrofit

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
        bind<UserApi>() with singleton { instance<Retrofit>().create(UserApi::class.java) }
        bind<ImageApi>() with singleton { instance<Retrofit>().create(ImageApi::class.java) }
        bind<EstimationApi>() with singleton { instance<Retrofit>().create(EstimationApi::class.java) }

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
                    instance<UserApi>(),
                    instance<ScheduleProvider>(),
                    instance<IErrorHandler>()
            )
        }

        bind<EstimationRepository>() with singleton {
            EstimationRepository(
                    instance<EstimationApi>(),
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