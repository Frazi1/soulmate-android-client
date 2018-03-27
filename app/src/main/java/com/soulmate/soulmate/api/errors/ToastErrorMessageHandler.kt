package com.soulmate.soulmate.api.errors

import android.content.Context
import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

class ToastErrorMessageHandler(kodein: Kodein, context: Context) : ErrorHandler(kodein) {
//    private val context: Context by instance()
    private val context: Context = context
    private val errorMessageExtractor: IErrorMessageExtractor by instance()


    override fun handle(t: HttpException) {
        Toast.makeText(context, errorMessageExtractor.errorMessage(t), Toast.LENGTH_LONG)
                .show()
    }
}