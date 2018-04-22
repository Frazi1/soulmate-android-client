package com.soulmate.soulmate.presentation.activity

import android.graphics.BitmapFactory
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.base.LoaderFragment
import com.soulmate.soulmate.presentation.presenter.ProfileEstimationPresenter
import com.soulmate.soulmate.presentation.view.IProfileEstimationView
import dtos.ProfileEstimationDto
import kotlinx.android.synthetic.main.fragment_profile_estimation.*

class ProfileEstimationFragment : LoaderFragment(), IProfileEstimationView {
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

    @BindView(R.id.estimation_imageViewAvatar)
    lateinit var imageViewAvatar: ImageView

    @BindView(R.id.estimation_textProfileName)
    lateinit var textProfileName: TextView

    @BindView(R.id.layout_profile_loading)
    override lateinit var loaderView: View

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_estimation, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    override fun displayProfileEstimation(profileEstimationDto: ProfileEstimationDto?) {
        if(profileEstimationDto == null)
        {
            estimation_layoutNoProfiles.visibility = View.VISIBLE
            return
        }
        estimation_layoutNoProfiles.visibility = View.GONE

        textProfileName.text = profileEstimationDto.firstName
        if(profileEstimationDto.profileImages.any()) {
            val imageBytes = profileEstimationDto.profileImages.first().data
            if (imageBytes != null) {
                val imageBitmap = BitmapFactory.decodeStream(imageBytes.inputStream())
                imageViewAvatar.setImageBitmap(imageBitmap)
            }
        }
    }

    @OnClick(R.id.estimation_buttonLike)
    fun likeProfile() {
        mProfileEstimationPresenter.likeProfile()
    }
}
