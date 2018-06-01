package com.soulmate.soulmate.presentation.view

import com.soulmate.soulmate.presentation.activity.chat.ChatDialog
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.view.base.IBaseMvpView
import com.soulmate.soulmate.presentation.view.base.ILoader

interface IChatView : IBaseMvpView, ILoader {
    fun displayDialogs(dialogs: List<ChatDialog<Message>>)
}
