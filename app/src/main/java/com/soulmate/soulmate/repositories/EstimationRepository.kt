package com.soulmate.soulmate.repositories

import com.soulmate.soulmate.api.EstimationApi
import com.soulmate.soulmate.api.errors.IErrorHandler
import dtos.ProfileEstimationDto
import io.reactivex.Observable
import okhttp3.ResponseBody


class EstimationRepository(private val estimationApi: EstimationApi, errorHandler: IErrorHandler) : BaseRepository(errorHandler) {

    fun getProfileEstimations(): Observable<Iterable<ProfileEstimationDto>> {
        return estimationApi.getAccountsForEstimation()
    }

    fun likeProfile(profileEstimationDto: ProfileEstimationDto): Observable<ResponseBody> {
        return estimationApi.likeProfile(profileEstimationDto.id)
    }
}