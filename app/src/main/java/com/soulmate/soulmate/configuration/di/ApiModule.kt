package com.soulmate.soulmate.configuration.di

import android.content.Context
import android.content.res.Resources
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.*
import com.soulmate.soulmate.CredentialsStore
import com.soulmate.soulmate.RetrofitProvider
import com.soulmate.soulmate.api.errors.HttpErrorMessageExtractor
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.api.errors.IErrorMessageExtractor
import com.soulmate.soulmate.api.errors.ToastErrorMessageHandler
import com.soulmate.soulmate.configuration.ConnectionPreferenceManager
import com.soulmate.soulmate.configuration.IConnectionPreferenceManager
import com.soulmate.soulmate.interaction.api.AuthApi
import com.soulmate.soulmate.interaction.api.EstimationApi
import com.soulmate.soulmate.interaction.api.ImageApi
import com.soulmate.soulmate.interaction.api.UserApi
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import com.soulmate.soulmate.presentation.validation.ValidationResponseHandler
import com.soulmate.soulmate.repositories.AuthRepository
import com.soulmate.soulmate.repositories.EstimationRepository
import com.soulmate.soulmate.repositories.ImageRepository
import com.soulmate.soulmate.repositories.UserRepository
import retrofit2.Retrofit


class KodeinModules {
    companion object {
        val apiModule = Kodein.Module {
            bind<AuthApi>() with singleton { instance<Retrofit>().create(AuthApi::class.java) }
            bind<UserApi>() with singleton { instance<Retrofit>().create(UserApi::class.java) }
            bind<ImageApi>() with singleton { instance<Retrofit>().create(ImageApi::class.java) }
            bind<EstimationApi>() with singleton { instance<Retrofit>().create(EstimationApi::class.java) }
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
        }

        fun configurationModule(context: Context) = Kodein.Module {
            bind<ObjectMapper>() with singleton { ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false) }

            bind<IConnectionPreferenceManager>() with singleton { ConnectionPreferenceManager(context, instance<Resources>()) }
            bind<CredentialsStore>() with singleton { CredentialsStore(context.getSharedPreferences("settings", Context.MODE_PRIVATE)) }

            bind<RetrofitProvider>() with singleton { RetrofitProvider(instance<ObjectMapper>(), instance<CredentialsStore>(), instance<IConnectionPreferenceManager>()) }
            bind<Retrofit>() with provider { instance<RetrofitProvider>().provide() }

            bind<IErrorMessageExtractor>() with singleton {
                HttpErrorMessageExtractor(
                        instance<ObjectMapper>(),
                        instance<IValidationResponseHandler>(),
                        instance<Resources>()
                )
            }
            bind<IErrorHandler>() with singleton { ToastErrorMessageHandler(context, instance<IErrorMessageExtractor>()) }
            bind<IValidationResponseHandler>() with singleton { ValidationResponseHandler() }
        }
    }
}