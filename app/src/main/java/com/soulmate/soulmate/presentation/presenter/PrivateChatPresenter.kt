package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.shared.dtos.SendMessageDto
import com.soulmate.shared.dtos.UserMessageDto
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.interaction.helpers.ImageUrlHelper
import com.soulmate.soulmate.presentation.activity.chat.Author
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.view.IPrivateChatView
import com.soulmate.soulmate.repositories.MessageRepository
import com.soulmate.soulmate.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import java.util.*
import java.util.concurrent.TimeUnit

@InjectViewState
class PrivateChatPresenter(lazyKodein: LazyKodein) : BasePresenter<IPrivateChatView>(lazyKodein) {

    private val messageRepository: MessageRepository by instance()
    private val userRepository: UserRepository by instance()
    private val userContextHolder: IUserContexHolder by instance()
    private val imageUrlHelper: ImageUrlHelper by instance()

    private var user: Author? = null
    private var colluctor: Author? = null
    private var lastMessageDate = Date(0)


    private fun constructMessage(msg: UserMessageDto): Message {
        val toLong = user!!.id.toLong()
        if (msg.fromUserId == toLong)
            return Message(msg, user!!)
        return Message(msg, colluctor!!)
    }

    fun loadMessagesWithUser(userId: Long) {
        userRepository.getUserProfiles(userId)
                .flatMap {
                    colluctor = Author(it.first(), imageUrlHelper)
                    user = Author(userContextHolder.user!!, imageUrlHelper)
                    return@flatMap messageRepository.getMessagesWithUser(userId)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally({
                    pollMessages()
                })
                .createSubscription({ messages ->
                    if (messages.isNotEmpty()) {
                        updateLastMessage(messages)
                        viewState.displayMessages(messages.map { constructMessage(it) })
                    }
                })
    }

    private fun pollMessages() {
        messageRepository.pollMessagesWithUser(colluctor!!.id.toLong(), lastMessageDate)
                .timeout(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally({ pollMessages() })
                .createSubscription({ messages ->
                    if (messages.isNotEmpty()) {
                        updateLastMessage(messages)
                        viewState.displayMessages(messages.map { constructMessage(it) }, true)
                    }
                }, {
//                    if(it !is java.util.concurrent.TimeoutException)
//                        defaultErrorHandler.handle(it)
                    //suppress timeout exceptions handling.
                })

    }

    private fun updateLastMessage(messages: List<UserMessageDto>) {
        val lastMessage = messages.sortedByDescending { it.sentAt }.first()
        lastMessageDate = lastMessage.sentAt
    }

    fun sendMessage(msg: String) {
        messageRepository.sendMessage(SendMessageDto(colluctor!!.id.toLong(), msg))
                .observeOn(AndroidSchedulers.mainThread())
                .createSubscription({})
    }

}