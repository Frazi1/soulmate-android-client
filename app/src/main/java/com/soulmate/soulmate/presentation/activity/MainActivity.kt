package com.soulmate.soulmate.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.base.BaseActivity
import kotlinx.android.synthetic.main.activity_main.*
import java.lang.Exception

class MainActivity : BaseActivity() {

    companion object {
        const val TAG = "MainActivity"
        fun getIntent(context: Context): Intent = Intent(context, MainActivity::class.java)
        const val POSITION_PROFILE = 0
        const val POSITION_ESTIMATION = 1
        const val POSITION_CHAT = 2
    }

    private lateinit var mSectionsPagerAdapter: SectionsPagerAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        setSupportActionBar(toolbar)
//        setSupportActionBar()

        // primary sections of the activity.
        mSectionsPagerAdapter = SectionsPagerAdapter(supportFragmentManager)

        // Set up the ViewPager with the sections adapter.
        container.adapter = mSectionsPagerAdapter

        container.addOnPageChangeListener(TabLayout.TabLayoutOnPageChangeListener(tabs))
        tabs.addOnTabSelectedListener(TabLayout.ViewPagerOnTabSelectedListener(container))

    }

    /**
     * A [FragmentPagerAdapter] that returns a fragment corresponding to
     * one of the sections/tabs/pages.
     */
    inner class SectionsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
        override fun getPageTitle(position: Int): CharSequence? {
            when (position) {
                POSITION_PROFILE -> return getString(R.string.tabName_Profile)
                POSITION_ESTIMATION -> return getString(R.string.tabName_AccountEstimations)
                POSITION_CHAT -> return getString(R.string.tabName_Chats)
            }
            return position.toString()
        }

        override fun getItem(position: Int): Fragment {
            when (position) {
                POSITION_PROFILE -> return ProfileFragment()
                POSITION_ESTIMATION -> return ProfileEstimationFragment()
                POSITION_CHAT -> return ChatFragment.newInstance()
            }
            throw Exception("No fragment $position")
        }

        override fun getCount(): Int {
            // Show 3 total pages.
            return 3
        }
    }
}
