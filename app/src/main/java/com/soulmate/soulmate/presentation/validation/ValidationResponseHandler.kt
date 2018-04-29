package com.soulmate.soulmate.presentation.validation

import validation.ValidationResponse


class ValidationResponseHandler : IValidationResponseHandler {
    override fun getValidatationMessage(v: ValidationResponse): String {
        val map = v.fieldErrors.map { "${it.code}" }
        val fieldsErrors = map.joinToString("\r\n")
        val map1 = v.globalErrors.map { "${it.code}" }
        val globalErrors = map1.joinToString("\r\n")
        return fieldsErrors + "\r\n" + globalErrors
    }
}