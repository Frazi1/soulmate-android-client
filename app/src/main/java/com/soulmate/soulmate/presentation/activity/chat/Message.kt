package com.soulmate.soulmate.presentation.activity.chat

import com.soulmate.shared.dtos.UserMessageDto
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser
import java.util.*


class Message(private val messageDto: UserMessageDto,
              private val author: Author) : IMessage {

    override fun getId(): String = messageDto.id.toString()
    override fun getCreatedAt(): Date = messageDto.sentAt
    override fun getUser(): IUser = author
    override fun getText(): String = messageDto.content
}