package com.soulmate.soulmate.interaction.api

import com.soulmate.shared.dtos.SendMessageDto
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.shared.dtos.UserMessageDto
import io.reactivex.Observable
import okhttp3.ResponseBody
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface MessageApi {
    @GET("api/message/{id}")
    fun getMessagesWithUser(@Path("id") userId: Long): Observable<List<UserMessageDto>>

    @GET("api/message")
    fun getUserChats() :Observable<List<UserAccountDto>>

    @POST("api/message")
    fun sendMessage(@Body message: SendMessageDto): Observable<UserMessageDto>
}
