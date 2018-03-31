package com.soulmate.soulmate.api.errors

interface IErrorHandler {
    fun handle(t: Throwable?)
}