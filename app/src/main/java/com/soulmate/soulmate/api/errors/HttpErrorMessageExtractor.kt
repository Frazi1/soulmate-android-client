package com.soulmate.soulmate.api.errors

import com.fasterxml.jackson.databind.ObjectMapper
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.api.HttpErrorCodes
import com.soulmate.soulmate.presentation.validation.IValidationResponseHandler
import validation.ValidationResponse

open class HttpErrorMessageExtractor(protected val kodein: Kodein) : IErrorMessageExtractor, KodeinInjected {
    override val injector: KodeinInjector = KodeinInjector()

    private val objectMapper: ObjectMapper by instance()
    private val validationResponseHandler: IValidationResponseHandler by instance()

    init {
        this.inject(kodein)
    }

    override fun errorMessage(t: Throwable): String {
        if (t is HttpException)
            return errorMessage(t)
        else
            throw t
    }

    open fun errorMessage(t: HttpException): String {
        return if (t.code() == HttpErrorCodes.UNPROCESSABLE_ENTITY.code) {
            return getUnprocessableEntityMessage(t)
        } else t.toString()
    }

    private fun getUnprocessableEntityMessage(t: HttpException): String {
        val body = t.response().errorBody()
        val v = objectMapper.readValue(body?.byteStream(), ValidationResponse::class.java)
        return validationResponseHandler.getValidatationMessage(v)
    }
}