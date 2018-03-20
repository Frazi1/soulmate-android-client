package com.soulmate.soulmate.ui.activity.profile

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView

import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import com.soulmate.soulmate.presentation.presenter.profile.ProfilePresenter

import com.arellomobile.mvp.MvpAppCompatActivity;


class ProfileActivity : MvpAppCompatActivity(), ProfileView {
    companion object {
        const val TAG = "ProfileActivity"
        fun getIntent(context: Context): Intent = Intent(context, ProfileActivity::class.java)
    }

    private lateinit var textUsername: TextView
    private lateinit var buttonSave: Button

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        textUsername = findViewById(R.id.profile_edit_username)
        buttonSave = findViewById(R.id.profile_button_save)

        buttonSave.setOnClickListener {mProfilePresenter.saveData(textUsername.text.toString())}
    }

    override fun setUsername(name: String) {
        textUsername.text = name
    }
}
