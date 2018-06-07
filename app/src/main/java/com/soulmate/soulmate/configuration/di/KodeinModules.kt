@file:Suppress("RemoveExplicitTypeArguments")

package com.soulmate.soulmate.configuration.di

import android.content.Context
import android.content.res.Resources
import android.preference.PreferenceManager
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.api.errors.HttpErrorMessageExtractor
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.api.errors.IErrorMessageExtractor
import com.soulmate.soulmate.api.errors.ToastErrorMessageHandler
import com.soulmate.soulmate.configuration.CredentialsStore
import com.soulmate.soulmate.configuration.Logger
import com.soulmate.soulmate.configuration.RetrofitProvider
import com.soulmate.soulmate.configuration.UserContextHolder
import com.soulmate.soulmate.configuration.interfaces.IConnectionPreferenceManager
import com.soulmate.soulmate.configuration.interfaces.ILogger
import com.soulmate.soulmate.configuration.interfaces.ISearchPreferencesManager
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.configuration.preferences.ConnectionPreferenceManager
import com.soulmate.soulmate.configuration.preferences.SearchPreferencesManager
import com.soulmate.soulmate.interaction.api.*
import com.soulmate.soulmate.interaction.api.errors.validation.IValidationResponseHandler
import com.soulmate.soulmate.interaction.api.errors.validation.ValidationResponseHandler
import com.soulmate.soulmate.interaction.authorization.AuthorizationInterceptor
import com.soulmate.soulmate.interaction.helpers.ImageUrlHelper
import com.soulmate.soulmate.interaction.helpers.PicassoWrapper
import com.soulmate.soulmate.repositories.*
import com.squareup.picasso.OkHttp3Downloader
import com.squareup.picasso.Picasso
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import java.util.concurrent.TimeUnit

val apiModule = Kodein.Module {
    bind<AuthApi>() with singleton { instance<Retrofit>().create(AuthApi::class.java) }
    bind<UserApi>() with singleton { instance<Retrofit>().create(UserApi::class.java) }
    bind<ImageApi>() with singleton { instance<Retrofit>().create(ImageApi::class.java) }
    bind<EstimationApi>() with singleton { instance<Retrofit>().create(EstimationApi::class.java) }
    bind<MessageApi>() with singleton { instance<Retrofit>().create(MessageApi::class.java) }
}

val repositoryModule = Kodein.Module {
    bind<AuthRepository>() with singleton {
        AuthRepository(
                instance<AuthApi>(),
                instance<IErrorHandler>()
        )
    }
    bind<ImageRepository>() with singleton {
        ImageRepository(
                instance<ImageApi>(),
                instance<IErrorHandler>()
        )
    }
    bind<UserRepository>() with singleton {
        UserRepository(
                instance<UserApi>(),
                instance<IErrorHandler>()
        )
    }

    bind<EstimationRepository>() with singleton {
        EstimationRepository(
                instance<EstimationApi>(),
                instance<IErrorHandler>()
        )
    }

    bind<MessageRepository>() with singleton {
        MessageRepository(
                instance<IErrorHandler>(),
                instance<MessageApi>()
        )
    }
}

fun configurationModule(context: Context) = Kodein.Module {
    bind<ObjectMapper>() with singleton { ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) }

    bind<IConnectionPreferenceManager>() with singleton { ConnectionPreferenceManager(context, instance<Resources>()) }
    bind<ISearchPreferencesManager>() with singleton { SearchPreferencesManager(PreferenceManager.getDefaultSharedPreferences(context)) }

    bind<CredentialsStore>() with singleton { CredentialsStore(context.getSharedPreferences("settings", Context.MODE_PRIVATE)) }

    bind<RetrofitProvider>() with singleton { RetrofitProvider(instance<ObjectMapper>(), instance<CredentialsStore>(), instance<IConnectionPreferenceManager>(), instance<OkHttpClient>()) }
    bind<Retrofit>() with provider { instance<RetrofitProvider>().provide() }
    bind<OkHttpClient>() with provider {
        val builder = OkHttpClient.Builder()
        builder.connectTimeout(1, TimeUnit.SECONDS)
        builder.readTimeout(30,TimeUnit.SECONDS)
        builder.addInterceptor(AuthorizationInterceptor(instance<CredentialsStore>()))
        val loggingInterceptor = HttpLoggingInterceptor()
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        builder.addInterceptor(loggingInterceptor)
//        builder.authenticator(TokenAuthenticator(kodein.lazy))
        return@provider builder.build()
    }

    bind<Picasso>() with singleton {
        Picasso.Builder(context)
                .downloader(OkHttp3Downloader(instance<OkHttpClient>()))
                .indicatorsEnabled(true)
                .build()
    }

    bind<PicassoWrapper>() with provider { PicassoWrapper(instance<Picasso>(), instance<ImageUrlHelper>(), instance<IErrorHandler>()) }

    bind<IErrorMessageExtractor>() with singleton {
        HttpErrorMessageExtractor(
                instance<ObjectMapper>(),
                instance<IValidationResponseHandler>(),
                instance<Resources>()
        )
    }
    bind<IErrorHandler>() with singleton { ToastErrorMessageHandler(context, instance<IErrorMessageExtractor>(), instance<ILogger>()) }
    bind<IValidationResponseHandler>() with singleton { ValidationResponseHandler() }

    bind<ImageUrlHelper>() with singleton { ImageUrlHelper(instance<IConnectionPreferenceManager>()) }
    bind<IUserContexHolder>() with singleton { UserContextHolder() }
    bind<ILogger>() with singleton { Logger() }
}
