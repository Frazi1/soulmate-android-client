package com.soulmate.soulmate.repositories

import com.github.salomonbrys.kodein.Kodein
import com.github.salomonbrys.kodein.KodeinInjected
import com.github.salomonbrys.kodein.KodeinInjector
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.configuration.ScheduleProvider


open class BaseRepository(kodein: Kodein): KodeinInjected {
    final override val injector: KodeinInjector = KodeinInjector()

    protected val scheduleProvider: ScheduleProvider by instance()
    protected val errorHandler: IErrorHandler by instance()

    init {
        injector.inject(kodein)
    }
}