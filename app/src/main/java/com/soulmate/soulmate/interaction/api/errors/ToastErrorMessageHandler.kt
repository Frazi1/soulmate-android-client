package com.soulmate.soulmate.api.errors

import android.content.Context
import android.widget.Toast
import com.soulmate.soulmate.configuration.interfaces.ILogger

class ToastErrorMessageHandler(private val context: Context,
                               private val errorMessageExtractor: IErrorMessageExtractor,
                               private val logger: ILogger) : IErrorHandler {


    override fun handle(t: Throwable?) {
        val message = if (t != null) errorMessageExtractor.errorMessage(t) else ""
//        if (t is HttpException) {
//            val reader = t.response().errorBody()?.charStream()?.buffered(DEFAULT_BUFFER_SIZE)
//            val text = reader?.readLines()?.joinToString(" ")
//            logger.error(text ?: "")
//        } else {
//            logger.error(t.toString() + "(${t?.message})")
//
        logger.error(message)
        Toast.makeText(context, message, Toast.LENGTH_SHORT)
                .show()
    }
}