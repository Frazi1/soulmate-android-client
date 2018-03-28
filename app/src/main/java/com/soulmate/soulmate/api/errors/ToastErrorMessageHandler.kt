package com.soulmate.soulmate.api.errors

import android.content.Context
import android.widget.Toast
import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.instance
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException

class ToastErrorMessageHandler(private val context: Context,
                               private val errorMessageExtractor: IErrorMessageExtractor) : ErrorHandler() {


    override fun handle(t: HttpException) {
        Toast.makeText(context, errorMessageExtractor.errorMessage(t), Toast.LENGTH_LONG)
                .show()
    }
}