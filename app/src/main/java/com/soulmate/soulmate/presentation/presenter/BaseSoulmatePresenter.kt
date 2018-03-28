package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.api.errors.IErrorHandler
import io.reactivex.Observable
import io.reactivex.disposables.Disposable


abstract class BaseSoulmatePresenter<T : MvpView>(final override val kodein: LazyKodein) : MvpPresenter<T>(), LazyKodeinAware {
    protected val defaultErrorHandler: IErrorHandler by instance()

    fun <TInput : Any> Observable<TInput>.subscribeWithErrorHandler(onSuccess: (TInput) -> Unit): Disposable {
        return this.subscribe(onSuccess, { defaultErrorHandler.handle(it) })
    }
}