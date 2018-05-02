package com.soulmate.soulmate.interaction.api.errors.validation

import com.soulmate.shared.validation.ValidationResponse


interface IValidationResponseHandler {
    fun getValidationMessage(v: ValidationResponse): String
}