package com.soulmate.soulmate.api.errors

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.jakewharton.retrofit2.adapter.rxjava2.HttpException
import com.soulmate.soulmate.App.Companion.instance
import io.reactivex.observers.DisposableObserver
import okhttp3.ResponseBody
import org.json.JSONObject
import java.io.IOException
import java.net.SocketTimeoutException

abstract class CallbackWrapper<T>(kodein: Kodein) : DisposableObserver<T>(), KodeinInjected {
    final override val injector: KodeinInjector = KodeinInjector()

    private val errorHandler: IErrorHandler by instance()

    init {
        this.injector.inject(kodein)
    }

    protected abstract fun onSuccess(t: T)

    override fun onNext(t: T) {
        onSuccess(t)
    }

    override fun onError(e: Throwable) {
        errorHandler.handle(e)
    }

    override fun onComplete() {

    }
}