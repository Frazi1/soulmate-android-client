package com.soulmate.soulmate.ui.activity.profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView

import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import com.soulmate.soulmate.presentation.presenter.profile.ProfilePresenter

import com.arellomobile.mvp.MvpAppCompatActivity;


class ProfileActivity : MvpAppCompatActivity(), ProfileView {
    companion object {
        const val TAG = "ProfileActivity"
        private const val PICK_IMAGE = 1
        fun getIntent(context: Context): Intent = Intent(context, ProfileActivity::class.java)
    }



    private lateinit var textUsername: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonUploadImage: FloatingActionButton
    private lateinit var imageViewAvatar: ImageView

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        textUsername = findViewById(R.id.profile_edit_username)
        buttonSave = findViewById(R.id.profile_button_save)
        buttonUploadImage = findViewById(R.id.profile_floatingButton_uploadImage)
        imageViewAvatar = findViewById(R.id.profile_imageView_avatar)

        buttonSave.setOnClickListener { mProfilePresenter.saveData(textUsername.text.toString()) }
        buttonUploadImage.setOnClickListener { selectImageFromStore() }
    }

    override fun setUsername(name: String) {
        textUsername.text = name
    }

    private fun selectImageFromStore() {
        val type = "image/*"

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = type

        val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = type

        val chooser = Intent.createChooser(intent, applicationContext.resources.getString(R.string.select_picture))
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayListOf(pickIntent))
        startActivityForResult(chooser,
                PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == PICK_IMAGE){
            val inputStream = applicationContext.contentResolver.openInputStream(data?.data)
            mProfilePresenter.addImage(inputStream)
        }
    }

    override fun showImage(bitmap: Bitmap) {
        imageViewAvatar.setImageBitmap(bitmap)
    }
}
