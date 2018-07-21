package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.shared.dtos.SendMessageDto
import com.soulmate.shared.dtos.UserMessageDto
import com.soulmate.soulmate.configuration.interfaces.IConnectionPreferenceManager
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.interaction.api.websocket.NotificationWebSocketListener
import com.soulmate.soulmate.interaction.helpers.ImageUrlHelper
import com.soulmate.soulmate.presentation.activity.chat.Author
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.view.IPrivateChatView
import com.soulmate.soulmate.repositories.MessageRepository
import com.soulmate.soulmate.repositories.UserRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.Disposable
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.WebSocket
import java.util.*
import java.util.concurrent.TimeUnit

@InjectViewState
class PrivateChatPresenter(lazyKodein: LazyKodein) : BasePresenter<IPrivateChatView>(lazyKodein) {

    private val messageRepository: MessageRepository by instance()
    private val userRepository: UserRepository by instance()
    private val userContextHolder: IUserContexHolder by instance()
    private val imageUrlHelper: ImageUrlHelper by instance()

    private var pollingDisposable: Disposable? = null

    private var user: Author? = null
    private var partner: Author? = null
    private var lastMessageDate = Date(0)
    private var isPolling = false

    var partnerUserId: Long = -1

    private fun constructMessage(msg: UserMessageDto): Message {
        val toLong = user!!.id.toLong()
        if (msg.fromUserId == toLong)
            return Message(msg, user!!)
        return Message(msg, partner!!)
    }

    private fun loadMessagesWithUser(userId: Long) {
        userRepository.getUserProfiles(userId)
                .flatMap {
                    partner = Author(it.first(), imageUrlHelper)
                    user = Author(userContextHolder.user!!, imageUrlHelper)
                    return@flatMap messageRepository.getMessagesWithUser(userId)
                }
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    if (!isPolling)
                        pollMessages()
                }
                .createSubscription({ messages ->
                    if (messages.isNotEmpty()) {
                        updateLastMessage(messages)
                        viewState.displayMessages(messages.map { constructMessage(it) })
                    }
                })
    }

    private fun pollMessages() {
        isPolling = true
        pollingDisposable = messageRepository.pollMessagesWithUser(partnerUserId, lastMessageDate)
                .timeout(30, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .doFinally {
                    isPolling = false
                }
                .createSubscription({ messages ->
                    if (messages.isNotEmpty()) {
                        updateLastMessage(messages)
                        viewState.displayMessages(messages.map { constructMessage(it) }, true)
                    }
                    pollMessages()

                }, {
                    if (it !is java.util.concurrent.TimeoutException) //suppress timeout exceptions handling.
                        defaultErrorHandler.handle(it)
                    pollMessages()
                })

    }

    private fun updateLastMessage(messages: List<UserMessageDto>) {
        val lastMessage = messages.sortedByDescending { it.sentAt }.first()
        lastMessageDate = lastMessage.sentAt
    }

    fun sendMessage(msg: String) {
        messageRepository.sendMessage(SendMessageDto(partner!!.id.toLong(), msg))
                .observeOn(AndroidSchedulers.mainThread())
                .createSubscription({})
    }

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
        loadMessagesWithUser(partnerUserId)
    }

    override fun detachView(view: IPrivateChatView?) {
        pollingDisposable?.dispose()
    }
}