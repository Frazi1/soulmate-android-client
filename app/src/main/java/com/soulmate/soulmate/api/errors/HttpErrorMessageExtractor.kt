package com.soulmate.soulmate.api.errors

import android.content.res.Resources
import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.R
import com.soulmate.soulmate.api.HttpErrorCodes
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import validation.ValidationResponse

open class HttpErrorMessageExtractor(private val objectMapper: ObjectMapper,
                                     private val validationResponseHandler: IValidationResponseHandler,
                                     private val resource: Resources) : IErrorMessageExtractor {

    override fun errorMessage(t: Throwable): String {
        if (t is HttpException)
            return errorMessage(t)
        else
            throw t
    }

    open fun errorMessage(t: HttpException): String {
        return when (t.code()) {
            HttpErrorCodes.UNPROCESSABLE_ENTITY.code -> getUnprocessableEntityMessage(t)
            HttpErrorCodes.NOT_AUTHORIZED.code -> resource.getString(R.string.invalid_credentials)
            else -> resource.getString(R.string.unknown_error)
        }
    }

    private fun getUnprocessableEntityMessage(t: HttpException): String {
        val body = t.response().errorBody()
        val v = objectMapper.readValue(body?.byteStream(), ValidationResponse::class.java)
        return validationResponseHandler.getValidatationMessage(v)
    }
}