package com.soulmate.soulmate.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife

import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.presentation.view.ISearchOptionsView
import com.soulmate.soulmate.presentation.presenter.SearchOptionsPresenter

import com.arellomobile.mvp.presenter.ProvidePresenter;
import com.github.salomonbrys.kodein.lazy
import com.soulmate.soulmate.App
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.base.BaseActivity
import io.apptik.widget.MultiSlider


class SearchOptionsActivity : BaseActivity(), ISearchOptionsView {
    companion object {
        const val TAG = "SearchOptions"
        fun getIntent(context: Context): Intent = Intent(context, SearchOptionsActivity::class.java)
    }

    @BindView(R.id.searchOptions_range_age) lateinit var sliderAge: MultiSlider
    @BindView(R.id.searchOptions_textView_minAge) lateinit var textViewMinAge: TextView
    @BindView(R.id.searchOptions_textView_maxAge) lateinit var textViewMaxAge: TextView

    @InjectPresenter lateinit var mSearchOptionsPresenter: SearchOptionsPresenter

    @ProvidePresenter
    fun providePresenter(): SearchOptionsPresenter = SearchOptionsPresenter(App.globalkodein.lazy)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_options)
        ButterKnife.bind(this)

        sliderAge.setOnThumbValueChangeListener { multiSlider, thumb, thumbIndex, value ->
            if(thumbIndex == 0)
                textViewMinAge.text = value.toString()
            else if(thumbIndex == 1)
                textViewMaxAge.text = value.toString()
        }
    }

    private fun onAgeSliderValueChanged(multiSlider: MultiSlider,
                                        thumb: MultiSlider.Thumb,
                                        thumbIndex: Int,
                                        value: Int){

    }
}
