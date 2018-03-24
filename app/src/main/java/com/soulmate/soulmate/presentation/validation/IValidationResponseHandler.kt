package com.soulmate.soulmate.presentation.validation

import validation.ValidationResponse

interface IValidationResponseHandler {
    fun getValidatationMessage(v: ValidationResponse): String
}