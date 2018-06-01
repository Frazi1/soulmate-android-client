package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.interaction.helpers.ImageUrlHelper
import com.soulmate.soulmate.presentation.activity.chat.Author
import com.soulmate.soulmate.presentation.activity.chat.ChatDialog
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.view.IChatView
import com.soulmate.soulmate.repositories.MessageRepository
import io.reactivex.android.schedulers.AndroidSchedulers

@InjectViewState
class ChatPresenter(lazyKodein: LazyKodein) : BasePresenter<IChatView>(lazyKodein) {
    private val messageRepository: MessageRepository by instance()
    private val urlHelper: ImageUrlHelper by instance()

    fun loadDialogs() {
        messageRepository.getUserChats()
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally({viewState.onFinishedLoading()})
                .createSubscription ({
                    val users = it.map { Author(it, urlHelper ) }
                    val dialogs: List<ChatDialog<Message>> = users.map { ChatDialog<Message>(it) }
                    viewState.displayDialogs(dialogs)
                })
    }
}
