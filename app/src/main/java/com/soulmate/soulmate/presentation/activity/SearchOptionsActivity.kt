package com.soulmate.soulmate.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.CheckBox
import android.widget.TextView
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick

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
    @BindView(R.id.searchOptions_checkBox_male) lateinit var checkBoxShowMale: CheckBox
    @BindView(R.id.searchOptions_checkBox_female) lateinit var checkBoxShowFemale: CheckBox

    @InjectPresenter lateinit var mSearchOptionsPresenter: SearchOptionsPresenter

    @ProvidePresenter
    fun providePresenter(): SearchOptionsPresenter = SearchOptionsPresenter(App.globalkodein.lazy)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_search_options)
        ButterKnife.bind(this)
        initDisplay()

        sliderAge.setOnThumbValueChangeListener { multiSlider, thumb, thumbIndex, value ->
            if(thumbIndex == 0) {
                mSearchOptionsPresenter.minAge = value
            } else if (thumbIndex == 1) {
                mSearchOptionsPresenter.maxAge = value
            }
            updateLabels()
        }
    }

    private fun updateLabels() {
        textViewMinAge.text = mSearchOptionsPresenter.minAge.toString()
        textViewMaxAge.text = mSearchOptionsPresenter.maxAge.toString()
    }

    private fun updateSlider() {
        sliderAge.getThumb(0).value = mSearchOptionsPresenter.minAge
        sliderAge.getThumb(1).value = mSearchOptionsPresenter.maxAge
    }

    private fun initDisplay() {
        updateLabels()
        updateSlider()
        updateCheckBoxes()
    }

    private fun updateCheckBoxes() {
        checkBoxShowMale.isChecked = mSearchOptionsPresenter.showMale
        checkBoxShowFemale.isChecked = mSearchOptionsPresenter.showFemale
    }

    @OnClick(R.id.searchOptions_checkBox_male)
    fun toggleShowMale() {
        mSearchOptionsPresenter.showMale = checkBoxShowMale.isChecked
    }

    @OnClick(R.id.searchOptions_checkBox_female)
    fun toggleShowFemale() {
        mSearchOptionsPresenter.showFemale = checkBoxShowFemale.isChecked
    }
}
