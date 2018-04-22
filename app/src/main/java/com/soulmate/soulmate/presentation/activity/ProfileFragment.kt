package com.soulmate.soulmate.presentation.activity

import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.os.Parcelable
import android.provider.MediaStore
import android.view.*
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.soulmate.soulmate.R
import com.soulmate.soulmate.presentation.activity.base.LoaderFragment
import com.soulmate.soulmate.presentation.presenter.ProfilePresenter
import com.soulmate.soulmate.presentation.view.IProfileView
import com.squareup.picasso.Picasso
import dtos.GenderType
import dtos.UserAccountDto

class ProfileFragment : LoaderFragment(), IProfileView {
    companion object {
        private const val PICK_IMAGE = 1
    }

    @BindView(R.id.profile_edit_username)
    lateinit var editTextUsername: TextView

    @BindView(R.id.profile_button_save)
    lateinit var buttonSave: Button

    @BindView(R.id.profile_button_logout)
    lateinit var buttonLogout: Button

    @BindView(R.id.profile_imageView_avatar)
    lateinit var imageViewAvatar: ImageView

    @BindView(R.id.profile_progressBar)
    lateinit var progressBar: ProgressBar

    @BindView(R.id.profile_editTextMultiline_personalStory)
    lateinit var editTextMultilinePersonalStory: EditText

    @BindView(R.id.layout_profile_loading)
    override lateinit var loaderView: View

    @InjectPresenter
    lateinit var mProfilePresenter: ProfilePresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_profile, container, false)
        ButterKnife.bind(this, view)
        return view
    }

    @OnClick(R.id.profile_button_save)
    fun saveProfile() {
        mProfilePresenter.saveProfile(
                editTextUsername.text.toString(),
                GenderType.NotDefined,
                editTextMultilinePersonalStory.text.toString())
    }

    @OnClick(R.id.profile_imageView_avatar)
    fun selectImage() {
        val type = "image/*"
        val intent = Intent(Intent.ACTION_GET_CONTENT)
        intent.type = type
        val pickIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        pickIntent.type = type
        val chooser = Intent.createChooser(intent, activity?.applicationContext?.resources?.getString(R.string.select_picture))
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayListOf<Parcelable>(pickIntent))
        startActivityForResult(chooser,
                PICK_IMAGE)
    }

    @OnClick(R.id.profile_button_logout)
    fun logout() {
        mProfilePresenter.logout()
    }

    @OnClick(R.id.layout_profile_loading)
    fun preventClickWhileLoading() {
        //empty
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PICK_IMAGE) {
            if (data != null) {
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

    override fun showImage(uri: Uri?) {
        Picasso.get()
                .load(uri)
                .into(imageViewAvatar)
    }

    override fun openLoginActivity() {
        val intent = LoginActivity.getIntent(context!!)
        intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(intent)
    }

    override fun showProfile(userAccount: UserAccountDto) {
        with(userAccount) {
            editTextUsername.text = firstName
            editTextMultilinePersonalStory.setText(personalStory)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_profile, menu)
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.button_menu_resetAllEstimations -> {
                mProfilePresenter.resetAllEstimations()
                true
            }
            else -> super.onContextItemSelected(item)
        }

    }
}