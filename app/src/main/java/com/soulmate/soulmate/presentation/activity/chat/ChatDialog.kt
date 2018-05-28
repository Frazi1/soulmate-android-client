package com.soulmate.soulmate.presentation.activity.chat

import com.soulmate.shared.dtos.UserMessageDto
import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.commons.models.IUser

class ChatDialog<TMessage : IMessage>(private val user: Author) : IDialog<TMessage> {

    private var _lastMessage: TMessage? = null

    override fun getDialogPhoto(): String = user.avatar
    override fun getId(): String = user.id

    override fun getUsers(): MutableList<out IUser> = mutableListOf(user)
    override fun getDialogName(): String = user.name

    override fun getLastMessage(): TMessage? {
        return Message(UserMessageDto(), user) as TMessage
    }

    override fun setLastMessage(message: TMessage) {
        _lastMessage = message
    }

    override fun getUnreadCount(): Int {
        //TODO: implement
        return 0
    }
}