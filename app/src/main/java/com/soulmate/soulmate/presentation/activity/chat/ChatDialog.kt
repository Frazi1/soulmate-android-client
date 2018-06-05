package com.soulmate.soulmate.presentation.activity.chat

import com.soulmate.shared.dtos.UserMessageDto
import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser

class ChatDialog<TMessage : IMessage>(private val user: Author,
                                      private var lastMessage: TMessage? = null) : IDialog<TMessage> {

    override fun getDialogPhoto(): String = user.avatar
    override fun getId(): String = user.id

    override fun getUsers(): MutableList<out IUser> = mutableListOf(user)
    override fun getDialogName(): String = user.name

    override fun getLastMessage(): TMessage {
        return lastMessage ?: Message(UserMessageDto(content = "No messages"), user) as TMessage
    }

    override fun setLastMessage(message: TMessage) {
        lastMessage = message
    }

    override fun getUnreadCount(): Int {
        //TODO: implement
        return 0
    }
}