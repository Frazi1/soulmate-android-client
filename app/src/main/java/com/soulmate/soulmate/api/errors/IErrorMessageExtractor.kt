package com.soulmate.soulmate.api.errors

interface IErrorMessageExtractor {
//    fun handle(t: Throwable)
    fun errorMessage(t: Throwable): String
}