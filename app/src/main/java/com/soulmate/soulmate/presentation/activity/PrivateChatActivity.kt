package com.soulmate.soulmate.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.LazyKodeinAware
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.R
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.presentation.activity.base.BaseActivity
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.presenter.PrivateChatPresenter
import com.soulmate.soulmate.presentation.view.IPrivateChatView
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.messages.MessageInput
import com.stfalcon.chatkit.messages.MessagesList
import com.stfalcon.chatkit.messages.MessagesListAdapter


class PrivateChatActivity : BaseActivity(), IPrivateChatView, LazyKodeinAware {
    companion object {
        const val TAG = "PrivateChatActivity"
        fun getIntent(context: Context): Intent = Intent(context, PrivateChatActivity::class.java)
    }

    override val kodein: LazyKodein = App.globalkodein.lazy

    private val userContextHolder: IUserContexHolder by instance()
    private val picasso: Picasso by instance()

    @BindView(R.id.privateChat_messagesList) lateinit var messagesList: MessagesList
    @BindView(R.id.privateChat_messageInput) lateinit var messageInput: MessageInput

    @InjectPresenter
    lateinit var mPrivateChatPresenter: PrivateChatPresenter

    @ProvidePresenter
    fun providePresenter(): PrivateChatPresenter = PrivateChatPresenter(App.globalkodein.lazy)

    lateinit var messagesListAdapter: MessagesListAdapter<Message>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_private_chat)
        ButterKnife.bind(this)

        messagesListAdapter = MessagesListAdapter(userContextHolder.user?.id.toString(), ImageLoader { imageView, url -> picasso.load(url).into(imageView) })
        messagesList.setAdapter(messagesListAdapter)
        val userIds: Array<Long>? = intent.getSerializableExtra("ids") as Array<Long>
        if (userIds != null)
            mPrivateChatPresenter.loadMessagesWithUser(userIds.first())

        messageInput.setInputListener { it: CharSequence ->
            mPrivateChatPresenter.sendMessage(it.toString())
            return@setInputListener true
        }
    }

    override fun displayMessages(messages: List<Message>) {
        messagesListAdapter.addToEnd(messages, true)
    }

    override fun addDisplayMessage(message: Message) {
        messagesListAdapter.addToStart(message, true)
    }
}
