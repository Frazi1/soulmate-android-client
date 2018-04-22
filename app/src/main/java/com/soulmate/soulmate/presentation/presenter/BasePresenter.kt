package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.api.errors.IErrorHandler
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


abstract class BasePresenter<T : MvpView>(final override val kodein: LazyKodein) : MvpPresenter<T>(), LazyKodeinAware {
    protected val defaultErrorHandler: IErrorHandler by instance()
    private val subscriptions: MutableCollection<Disposable> = mutableListOf()


    fun <T : Any> Observable<T>.createSubscription(onDo: (T) -> Unit,
                                                   onError: (Throwable) -> Unit = defaultErrorHandler::handle) {
        val s: Disposable = subscribe(onDo, onError)
        subscriptions.add(s)
    }

    override fun onDestroy() {
        subscriptions.forEach { it.dispose() }
        subscriptions.clear()
    }

    //    fun <TInput : Any> Observable<TInput>.subscribeWithDefaultErrorHandler(onSuccess: (TInput) -> Unit): Disposable {
//        return this.subscribeWithErrorHandler(onSuccess, {}, {}, defaultErrorHandler::handle)
//    }

//    fun <TInput : Any> Observable<TInput>.subscribeWithDefaultErrorHandler(onSuccess: (TInput) -> Unit = {},
//                                                                           onError: () -> Unit = {},
//                                                                           doFinally: () -> Unit = {}): Disposable {
//        return this.subscribeWithErrorHandler(onSuccess, onError, doFinally, defaultErrorHandler::handle)
//    }
//
//    fun <TInput : Any> Observable<TInput>.subscribeWithErrorHandler(onSuccess: (TInput) -> Unit,
//                                                                    onError: () -> Unit,
//                                                                    doFinally: () -> Unit,
//                                                                    errorHandler: (Throwable?) -> Unit): Disposable {
//        return this
//                .doFinally(doFinally)
//                .doOnError({ t ->
//                    onError()
//                    errorHandler(t)
//                })
//                .subscribe(onSuccess, {
//                    errorHandler(it)
//                })
//    }

}