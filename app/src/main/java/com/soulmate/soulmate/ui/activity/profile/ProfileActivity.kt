package com.soulmate.soulmate.ui.activity.profile

import android.content.Context
import android.content.Intent
import android.content.res.AssetFileDescriptor
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.ParcelFileDescriptor
import android.os.Parcelable
import android.support.design.widget.FloatingActionButton
import android.view.Menu
import android.view.View
import android.widget.*
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.presenter.profile.ProfilePresenter
import com.soulmate.soulmate.presentation.view.profile.ProfileView
import com.soulmate.soulmate.ui.activity.BaseSoulmateActivity
import com.soulmate.soulmate.ui.activity.login.LoginActivity
import com.squareup.picasso.Picasso


class ProfileActivity : BaseSoulmateActivity(), ProfileView {
    companion object {
        const val TAG = "ProfileActivity"
        private const val PICK_IMAGE = 1
        fun getIntent(context: Context): Intent = Intent(context, ProfileActivity::class.java)
    }

    private lateinit var textUsername: TextView
    private lateinit var buttonSave: Button
    private lateinit var buttonLogout: Button
    private lateinit var buttonUploadImage: FloatingActionButton
    private lateinit var imageViewAvatar: ImageView
    private lateinit var progressBar: ProgressBar

    private lateinit var layoutLoading: FrameLayout

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_profile)

        textUsername = findViewById(R.id.profile_edit_username)
        buttonSave = findViewById(R.id.profile_button_save)
        buttonLogout = findViewById(R.id.profile_button_logout)
        buttonUploadImage = findViewById(R.id.profile_floatingButton_uploadImage)
        imageViewAvatar = findViewById(R.id.profile_imageView_avatar)
        progressBar = findViewById(R.id.profile_progressBar)
        layoutLoading = findViewById(R.id.layout_profile_loading)


        buttonSave.setOnClickListener { mProfilePresenter.saveData(textUsername.text.toString()) }
        buttonUploadImage.setOnClickListener { selectImageFromStore() }
        buttonLogout.setOnClickListener {mProfilePresenter.logout()}
        layoutLoading.setOnClickListener { }
        setSpinnerVisibility(false)
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        return super.onCreateOptionsMenu(menu)
    }

    override fun setUsername(name: String?) {
        textUsername.text = name
    }

    private fun selectImageFromStore() {
        val type = "image/*"

        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = type

        val pickIntent = Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = type

        val chooser = Intent.createChooser(intent, applicationContext.resources.getString(R.string.select_picture))
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayListOf<Parcelable>(pickIntent))
        startActivityForResult(chooser,
                PICK_IMAGE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            if(data!= null) {
                val uri = data.data
                mProfilePresenter.addImage(uri)
                showImage(uri)
            }
        }
    }

    override fun showImage(bitmap: Bitmap) {
        imageViewAvatar.setImageBitmap(bitmap)
//        imageViewAvatar.adjustViewBounds = true
//        imageViewAvatar.scaleType = ImageView.ScaleType.FIT_CENTER
    }

    override fun setSpinnerVisibility(isVisible: Boolean) {
        if (isVisible) {
            progressBar.visibility = View.VISIBLE
            layoutLoading.visibility = View.VISIBLE
        } else {
            progressBar.visibility = View.GONE
            layoutLoading.visibility = View.GONE
        }
    }

    override fun showImage(uri: Uri?) {
        Picasso.get()
                .load(uri)
                .into(imageViewAvatar)
    }

    override fun openLoginActivity() {
        val intent = LoginActivity.getIntent(this)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }
}
