package com.soulmate.soulmate.api

import com.soulmate.soulmate.R

enum class HttpErrorCodes(val code: Int) {
    NOT_AUTHORIZED(401),
    UNPROCESSABLE_ENTITY(422)
}