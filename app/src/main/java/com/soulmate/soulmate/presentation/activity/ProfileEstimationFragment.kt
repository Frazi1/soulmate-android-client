package com.soulmate.soulmate.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import butterknife.ButterKnife
import com.soulmate.soulmate.presentation.view.IProfileEstimationView
import com.soulmate.soulmate.presentation.presenter.ProfileEstimationPresenter

import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.base.BaseFragment

class ProfileEstimationFragment : BaseFragment(), IProfileEstimationView {
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

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile_estimation, container, false)
        ButterKnife.bind(this, view)
        return view
    }
}
