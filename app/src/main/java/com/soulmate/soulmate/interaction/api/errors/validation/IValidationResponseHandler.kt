package com.soulmate.soulmate.interaction.api.errors.validation

import validation.ValidationResponse

interface IValidationResponseHandler {
    fun getValidationMessage(v: ValidationResponse): String
}