package com.soulmate.soulmate.repositories

import com.soulmate.shared.Estimation
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.interaction.api.EstimationApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import io.reactivex.Observable
import okhttp3.ResponseBody


class EstimationRepository(private val estimationApi: EstimationApi, errorHandler: IErrorHandler) : BaseRepository(errorHandler) {

    fun getUsersForEstimation(filters: Map<String, String>): Observable<Iterable<UserAccountDto>> {
        return estimationApi.getUsersForEstimation(filters)
    }

    fun estimateUser(accountId: Long, estimation: Estimation): Observable<ResponseBody> {
        return estimationApi.estimateUser(accountId, estimation)
    }

    fun resetAllEstimations(): Observable<ResponseBody> {
        return estimationApi.resetAllEstimations()
    }
}