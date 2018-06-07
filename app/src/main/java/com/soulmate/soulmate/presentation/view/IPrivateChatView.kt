package com.soulmate.soulmate.presentation.view

import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.view.base.IBaseMvpView

interface IPrivateChatView : IBaseMvpView {
    fun displayMessages(messages: List<Message>, addToStart: Boolean = false)
    fun addDisplayMessage(message: Message)
}
