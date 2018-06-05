package com.soulmate.soulmate.repositories

import com.soulmate.shared.dtos.SendMessageDto
import com.soulmate.shared.dtos.UserDialogDto
import com.soulmate.shared.dtos.UserMessageDto
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.soulmate.soulmate.interaction.api.MessageApi
import io.reactivex.Observable
import java.util.*


class MessageRepository(errorHandler: IErrorHandler,
                        private val messageApi: MessageApi) : BaseRepository(errorHandler) {

    fun getUserDialogs(): Observable<List<UserDialogDto>> {
        return messageApi.getUserDialogs()
    }

    fun getMessagesWithUser(userId: Long): Observable<List<UserMessageDto>> {
        return messageApi.getMessagesWithUser(userId)
    }

    fun pollMessagesWithUser(userId: Long, dateAfter: Date): Observable<List<UserMessageDto>> {
        return messageApi.pollMessagesWithUser(userId, dateAfter.time)
    }

    fun sendMessage(sendMessageDto: SendMessageDto): Observable<UserMessageDto> {
        return messageApi.sendMessage(sendMessageDto)
    }
}