package com.soulmate.soulmate.api.errors

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

abstract class ErrorHandler(): IErrorHandler{
    override fun handle(t: Throwable?) {
        if( t is HttpException)
            handle(t)
    }

    abstract fun handle(t: HttpException)
}