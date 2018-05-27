package com.soulmate.soulmate.repositories

import com.soulmate.shared.dtos.SendMessageDto
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.shared.dtos.UserMessageDto
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.interaction.api.MessageApi
import io.reactivex.Observable
import okhttp3.ResponseBody


class MessageRepository(errorHandler: IErrorHandler,
                        private val messageApi: MessageApi) : BaseRepository(errorHandler) {

    fun getUserChats(): Observable<List<UserAccountDto>> {
        return messageApi.getUserChats()
    }

    fun getMessagesWithUser(userId: Long): Observable<List<UserMessageDto>> {
        return messageApi.getMessagesWithUser(userId)
    }

    fun sendMessage(sendMessageDto: SendMessageDto): Observable<ResponseBody> {
        return messageApi.sendMessage(sendMessageDto)
    }
}