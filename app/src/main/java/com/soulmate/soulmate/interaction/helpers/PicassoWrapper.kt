package com.soulmate.soulmate.interaction.helpers

import android.widget.ImageView
//import com.soulmate.soulmate.R
import com.soulmate.soulmate.api.errors.IErrorHandler
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import java.lang.Exception

class PicassoWrapper(private val picasso: Picasso,
                     private val imageUrlHelper: ImageUrlHelper,
                     private val errorHandler: IErrorHandler) {
    companion object {
//        private const val defaultPlaceHolder = R.drawable.ic_launcher_background;
    }

    fun fetchImage(imageId: Long) {
        picasso
                .load(imageUrlHelper.getImageUrl(imageId))
                .fetch()
    }

    fun fetchAndDisplay(imageId: Long, imageView: ImageView, placeHolder: Int? = null)
            = fetchAndDisplay(imageUrlHelper.getImageUrl(imageId), imageView, placeHolder)

    fun fetchAndDisplay(url: String, imageView: ImageView, placeHolder: Int? = null) {
        picasso
                .load(url)
                .fetch(object : Callback {
                    override fun onSuccess() {
                        var rc = picasso
                                .load(url)
                        if (placeHolder != null)
                            rc = rc.placeholder(placeHolder)

                        rc.into(imageView)

                    }

                    override fun onError(e: Exception?) {
                        errorHandler.handle(e)
                    }
                })
    }
}