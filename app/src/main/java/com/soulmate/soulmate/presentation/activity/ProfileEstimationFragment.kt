package com.soulmate.soulmate.presentation.activity

import android.os.Bundle
import android.support.v4.widget.SwipeRefreshLayout
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.github.salomonbrys.kodein.lazy
import com.soulmate.shared.Estimation
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.App
import com.soulmate.soulmate.R
import com.soulmate.soulmate.interaction.helpers.PicassoWrapper
import com.soulmate.soulmate.presentation.activity.base.LoaderFragment
import com.soulmate.soulmate.presentation.presenter.ProfileEstimationPresenter
import com.soulmate.soulmate.presentation.view.IProfileEstimationView
import kotlinx.android.synthetic.main.fragment_profile_estimation.*

class ProfileEstimationFragment : LoaderFragment(), IProfileEstimationView {

    private val picassoWrapper: PicassoWrapper by instance()

    companion object {
        const val TAG = "ProfileEstimationFragment"

        fun newInstance(): ProfileEstimationFragment {
            val fragment: ProfileEstimationFragment = ProfileEstimationFragment()
            val args: Bundle = Bundle()
            fragment.arguments = args
            return fragment
        }
    }

    @InjectPresenter
    lateinit var mProfileEstimationPresenter: ProfileEstimationPresenter

    @BindView(R.id.estimation_imageViewAvatar) lateinit var imageViewAvatar: ImageView
    @BindView(R.id.estimation_textProfileName) lateinit var textProfileName: TextView
    @BindView(R.id.estimation_textView_profileDescription) lateinit var textViewProfileDescription: TextView
    @BindView(R.id.layout_profile_loading) override lateinit var loaderView: View
    @BindView(R.id.estimation_refreshLayout) lateinit var refreshLayout: SwipeRefreshLayout


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        super.onCreateView(inflater, container, savedInstanceState)
        val view = inflater.inflate(R.layout.fragment_profile_estimation, container, false)
        ButterKnife.bind(this, view)
        refreshLayout.setOnRefreshListener { mProfileEstimationPresenter.loadEstimationProfiles() }
        return view
    }

    override fun displayUserAccount(profileEstimationDto: UserAccountDto?) {
        if (profileEstimationDto == null) {
            estimation_layoutNoProfiles.visibility = View.VISIBLE
            return
        }
        estimation_layoutNoProfiles.visibility = View.GONE

        textProfileName.text = profileEstimationDto.firstName
        textViewProfileDescription.text = profileEstimationDto.personalStory

        if (profileEstimationDto.profileImages.any()) {
            picassoWrapper.fetchAndDisplay(
                    profileEstimationDto.profileImages.first().imageId,
                    imageViewAvatar,
                    R.drawable.ic_launcher_background)
        } else {
            imageViewAvatar.setBackgroundColor(resources.getColor(R.color.colorNoContent))
            imageViewAvatar.setImageResource(0)
        }
    }

    @OnClick(R.id.estimation_buttonLike)
    fun likeUser() {
        mProfileEstimationPresenter.estimateUser(Estimation.LIKE)
    }

    @OnClick(R.id.estimation_buttonDislike)
    fun dislikeUser() {
        mProfileEstimationPresenter.estimateUser(Estimation.DISLIKE)
    }

    override fun onFinishedLoading() {
        super.onFinishedLoading()
        estimation_refreshLayout.isRefreshing = false
    }
}
