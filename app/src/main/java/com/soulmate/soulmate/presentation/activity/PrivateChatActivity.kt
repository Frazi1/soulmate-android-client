package com.soulmate.soulmate.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.R
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.interaction.helpers.PicassoWrapper
import com.soulmate.soulmate.presentation.activity.base.BaseActivity
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.presenter.PrivateChatPresenter
import com.soulmate.soulmate.presentation.view.IPrivateChatView
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter


class PrivateChatActivity : BaseActivity(), IPrivateChatView {
    companion object {
        const val TAG = "PrivateChatActivity"
        fun getIntent(context: Context): Intent = Intent(context, PrivateChatActivity::class.java)
    }

    private val userContextHolder: IUserContexHolder by instance()
    private val picassoWrapper: PicassoWrapper by instance()

    @BindView(R.id.privateChat_messagesList) lateinit var messagesList: MessagesList
    @BindView(R.id.privateChat_messageInput) lateinit var messageInput: MessageInput

    @InjectPresenter
    lateinit var mPrivateChatPresenter: PrivateChatPresenter

    @ProvidePresenter
    fun providePresenter(): PrivateChatPresenter = PrivateChatPresenter(kodein)

    lateinit var messagesListAdapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)
        ButterKnife.bind(this)

        messagesListAdapter = MessagesListAdapter(
                userContextHolder.user?.id.toString(),
                ImageLoader { imageView, url -> picassoWrapper.fetchAndDisplay(url, imageView) })
        messagesList.setAdapter(messagesListAdapter)
        val userIds: Array<Long>? = intent.getSerializableExtra("ids") as Array<Long>
        if (userIds != null)
            mPrivateChatPresenter.partnerUserId = userIds.first()

        messageInput.setInputListener { it: CharSequence ->
            mPrivateChatPresenter.sendMessage(it.toString())
            return@setInputListener true
        }
    }

    override fun displayMessages(messages: List<Message>, addToStart: Boolean) {
        if (addToStart)
            messages.forEach { messagesListAdapter.addToStart(it, true) }
        else
            messagesListAdapter.addToEnd(messages, true)
    }

    override fun addDisplayMessage(message: Message) {
        messagesListAdapter.addToStart(message, true)
    }
}
