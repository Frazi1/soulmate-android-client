package com.soulmate.soulmate.interaction.api

import com.soulmate.shared.dtos.SendMessageDto
import com.soulmate.shared.dtos.UserDialogDto
import com.soulmate.shared.dtos.UserMessageDto
import io.reactivex.Observable
import retrofit2.http.*
import java.util.*

interface MessageApi {
    @GET("api/message/poll/{id}")
    fun pollMessagesWithUser(@Path("id") userId: Long,
                             @Query("dateAfter") dateAfter: Long): Observable<List<UserMessageDto>>

    @GET("api/message/{id}")
    fun getMessagesWithUser(@Path("id") userId: Long): Observable<List<UserMessageDto>>

    @GET("api/message")
    fun getUserDialogs() :Observable<List<UserDialogDto>>

    @POST("api/message")
    fun sendMessage(@Body message: SendMessageDto): Observable<UserMessageDto>
}
