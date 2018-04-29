package com.soulmate.soulmate.interaction.api.errors.validation

import validation.ValidationResponse


class ValidationResponseHandler : IValidationResponseHandler {
    override fun getValidationMessage(v: ValidationResponse): String {
        val fieldsErrors = v.fieldErrors.joinToString("\r\n") { "${it.code}" }
        val globalErrors = v.globalErrors.joinToString("\r\n") { "${it.code}" }
        return fieldsErrors + "\r\n" + globalErrors
    }
}