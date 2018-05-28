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

@InjectViewState
class PrivateChatPresenter(lazyKodein: LazyKodein) : BasePresenter<IPrivateChatView>(lazyKodein) {

    private val messageRepository: MessageRepository by instance()
    private val userRepository: UserRepository by instance()
    private val userContextHolder: IUserContexHolder by instance()
    private val imageUrlHelper: ImageUrlHelper by instance()

    private var user: Author? = null
    private var colluctor: Author? = null


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
                    messageRepository.getMessagesWithUser(userId)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .createSubscription({ messages ->
                    if (messages.isNotEmpty()) {
                        viewState.displayMessages(messages.map { constructMessage(it) })
                    }
                })
    }

    fun sendMessage(msg: String) {
        messageRepository.sendMessage(SendMessageDto(colluctor!!.id.toLong(), msg))
                .observeOn(AndroidSchedulers.mainThread())
                .createSubscription({
                    viewState.addDisplayMessage(constructMessage(it))
                })
    }

}