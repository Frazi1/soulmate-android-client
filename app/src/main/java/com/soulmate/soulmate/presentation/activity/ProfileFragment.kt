package com.soulmate.soulmate.presentation.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.*
import android.widget.*
import butterknife.BindView
import butterknife.ButterKnife
import butterknife.OnClick
import com.arellomobile.mvp.presenter.InjectPresenter
import com.github.salomonbrys.kodein.instance
import com.soulmate.shared.GenderType
import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.R
import com.soulmate.soulmate.interaction.helpers.ImageUrlHelper
import com.soulmate.soulmate.presentation.activity.base.LoaderFragment
import com.soulmate.soulmate.presentation.presenter.ProfilePresenter
import com.soulmate.soulmate.presentation.view.IProfileView
import com.squareup.picasso.Picasso

class ProfileFragment : LoaderFragment(), IProfileView {

    companion object {
        private const val PICK_IMAGE = 1
    }

    private val picasso: Picasso by instance()
    private val urlHelper: ImageUrlHelper by instance()

    @BindView(R.id.profile_edit_username) lateinit var editTextUsername: TextView
    @BindView(R.id.profile_button_save) lateinit var buttonSave: Button
    @BindView(R.id.profile_button_logout) lateinit var buttonLogout: Button
    @BindView(R.id.profile_imageView_avatar) lateinit var imageViewAvatar: ImageView
    @BindView(R.id.profile_progressBar) lateinit var progressBar: ProgressBar
    @BindView(R.id.profile_editTextMultiline_personalStory) lateinit var editTextMultilinePersonalStory: EditText
    @BindView(R.id.layout_profile_loading) override lateinit var loaderView: View
    @BindView(R.id.profile_rbutton_Male) lateinit var rbuttonMale: RadioButton
    @BindView(R.id.profile_rbutton_Female) lateinit var rbuttonFemale: RadioButton

    @InjectPresenter lateinit var mProfilePresenter: ProfilePresenter

    private var userGender: GenderType = GenderType.NotDefined

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
                userGender,
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
        chooser.putExtra(Intent.EXTRA_INITIAL_INTENTS, arrayOf(pickIntent))
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

    @OnClick(R.id.profile_rbutton_Male, R.id.profile_rbutton_Female)
    fun onGenderRadioButtonClick(view: View) {
        val isChecked = (view as RadioButton).isChecked
        if (isChecked) {
            when (view.id) {
                R.id.profile_rbutton_Male -> userGender = GenderType.Male
                R.id.profile_rbutton_Female -> userGender = GenderType.Female
            }
        }
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
            setGenderRadioSelection(userAccount.gender)
            if (profileImages.any()) {
                picasso
                        .load(urlHelper.getImageUrl(profileImages.first().imageId))
                        .placeholder(R.drawable.ic_launcher_background)
                        .into(imageViewAvatar)
            }
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
            R.id.button_menu_searchOptions -> {
                context?.let { startActivity(SearchOptionsActivity.getIntent(it)) }
                true
            }
            else -> super.onContextItemSelected(item)
        }
    }

    private fun setGenderRadioSelection(genderType: GenderType) {
        when (genderType) {
            GenderType.Male -> rbuttonMale.isChecked = true
            GenderType.Female -> rbuttonFemale.isChecked = true
        }
    }
}
