package com.soulmate.soulmate.presentation.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.BindView
import butterknife.ButterKnife
import com.arellomobile.mvp.presenter.InjectPresenter
import com.arellomobile.mvp.presenter.ProvidePresenter
import com.github.salomonbrys.kodein.instance
import com.soulmate.soulmate.R
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder
import com.soulmate.soulmate.interaction.helpers.PicassoWrapper
import com.soulmate.soulmate.presentation.activity.base.BaseFragment
import com.soulmate.soulmate.presentation.activity.chat.ChatDialog
import com.soulmate.soulmate.presentation.activity.chat.Message
import com.soulmate.soulmate.presentation.presenter.ChatPresenter
import com.soulmate.soulmate.presentation.view.IChatView
import com.stfalcon.chatkit.commons.ImageLoader
import com.stfalcon.chatkit.dialogs.DialogsList
import com.stfalcon.chatkit.dialogs.DialogsListAdapter


class ChatFragment : BaseFragment(), IChatView {
    private val picassoWrapper: PicassoWrapper by instance()
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

    @BindView(R.id.chat_dialogsList)
    lateinit var dialogsList: DialogsList
    @BindView(R.id.chats_refreshLayout)
    lateinit var refreshLayout: SwipeRefreshLayout

    @ProvidePresenter
    fun providePresenter(): ChatPresenter = ChatPresenter(kodein)

    private lateinit var dialogsListAdapter: DialogsListAdapter<ChatDialog<Message>>


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_chat, container, false)
        ButterKnife.bind(this, view)
        refreshLayout.setOnRefreshListener { mChatPresenter.loadDialogs() }
        dialogsListAdapter = DialogsListAdapter(ImageLoader { imageView, url -> picassoWrapper.fetchAndDisplay(url, imageView) })
        dialogsList.setAdapter(dialogsListAdapter)
        dialogsListAdapter.setOnDialogClickListener {
            val intent = PrivateChatActivity.getIntent(context!!)
            val array: List<Long> = it.users
                    .map { it.id.toLong() }
                    .minus(userContextHolder.user?.id)
                    .mapNotNull { it }
            intent.putExtra("ids", array.toTypedArray())
            startActivity(intent)
        }
        mChatPresenter.loadDialogs()
        return view
    }

    override fun displayDialogs(dialogs: List<ChatDialog<Message>>) {
        dialogsListAdapter.setItems(dialogs)
    }

    override fun onLoading() {
        refreshLayout.isRefreshing = true
    }

    override fun onFinishedLoading() {
        refreshLayout.isRefreshing = false
    }
}
