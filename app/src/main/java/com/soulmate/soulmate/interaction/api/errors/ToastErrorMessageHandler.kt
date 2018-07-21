package com.soulmate.soulmate.api.errors

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.App
import com.soulmate.soulmate.configuration.CredentialsStore
import com.soulmate.soulmate.configuration.interfaces.ILogger
import com.soulmate.soulmate.presentation.activity.LoginActivity

class ToastErrorMessageHandler(private val context: Context,
                               private val errorMessageExtractor: IErrorMessageExtractor,
                               private val logger: ILogger,
                               private val credentialsStore: CredentialsStore,
                               private val activity: Activity? = null) : IErrorHandler {

    private fun handleNotAuthorizedException() {
        App.instance.stopNotificationService()
        credentialsStore.clear()

        val intent = LoginActivity.getIntent(context)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        context.startActivity(intent)
    }

    override fun handle(t: Throwable?) {
        val message = if (t != null) errorMessageExtractor.errorMessage(t) else ""
//        if (t is HttpException) {
//            val reader = t.response().errorBody()?.charStream()?.buffered(DEFAULT_BUFFER_SIZE)
//            val text = reader?.readLines()?.joinToString(" ")
//            logger.error(text ?: "")
//        } else {
//            logger.error(t.toString() + "(${t?.message})")
//
        if(t is HttpException && t.code() == 401){
            handleNotAuthorizedException()
        }
        logger.error(message)
        val toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
//                .show()
        if (activity != null) {
            activity.runOnUiThread {
                toast.show()
            }
        } else {
            toast.show()
        }
    }
}