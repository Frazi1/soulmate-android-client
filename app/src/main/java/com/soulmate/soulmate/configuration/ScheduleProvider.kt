package com.soulmate.soulmate.configuration

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers

class ScheduleProvider {
    fun provide(): Scheduler = AndroidSchedulers.mainThread()
}