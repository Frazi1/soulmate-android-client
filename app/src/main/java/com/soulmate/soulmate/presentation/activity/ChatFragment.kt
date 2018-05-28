package com.soulmate.soulmate.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.R
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.presentation.activity.base.BaseFragment
import com.soulmate.soulmate.presentation.activity.chat.ChatDialog
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.presenter.ChatPresenter
import com.soulmate.soulmate.presentation.view.IChatView
import com.squareup.picasso.Picasso
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.commons.models.IDialog
import com.stfalcon.chatkit.commons.models.IMessage
import com.stfalcon.chatkit.dialogs.DialogsList
import com.stfalcon.chatkit.dialogs.DialogsListAdapter


class ChatFragment : BaseFragment(), IChatView {
    override val kodein: LazyKodein = App.globalkodein.lazy

    private val picasso: Picasso by instance()
    private val userContextHolder: IUserContexHolder by instance()

    companion object {
        const val TAG = "ChatFragment"

        fun newInstance(): ChatFragment {
            val fragment: ChatFragment = ChatFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mChatPresenter: ChatPresenter

    @BindView(R.id.chat_dialogsList) lateinit var dialogsList: DialogsList

    @ProvidePresenter
    fun providePresenter(): ChatPresenter = ChatPresenter(kodein)

    lateinit var dialogsListAdapter: DialogsListAdapter<ChatDialog<Message>>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        ButterKnife.bind(this, view)
        dialogsListAdapter = DialogsListAdapter(ImageLoader { imageView, url -> picasso.load(url).into(imageView) })
        dialogsList.setAdapter(dialogsListAdapter)
        dialogsListAdapter.setOnDialogClickListener {
            val intent = PrivateChatActivity.getIntent(context!!)
            val array: List<Long> = it.users
                    .map { it.id.toLong() }
                    .minus(userContextHolder.user?.id)
                    .mapNotNull { it }
            val typed = array.toTypedArray()
            intent.putExtra("ids", typed)
            startActivity(intent)
        }
        mChatPresenter.loadDialogs()
        return view
    }

    override fun displayDialogs(dialogs: List<ChatDialog<Message>>) {
        dialogsListAdapter.setItems(dialogs)
    }
}
