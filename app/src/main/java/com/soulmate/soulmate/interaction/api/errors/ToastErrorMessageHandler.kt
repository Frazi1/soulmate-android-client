package com.soulmate.soulmate.api.errors

import android.content.Context
import android.widget.Toast

class ToastErrorMessageHandler(private val context: Context,
                               private val errorMessageExtractor: IErrorMessageExtractor) : IErrorHandler {


    override fun handle(t: Throwable?) {
        val message = if (t != null) errorMessageExtractor.errorMessage(t) else ""
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .show()
    }
}