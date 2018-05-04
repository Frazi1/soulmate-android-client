package com.soulmate.soulmate.interaction.helpers

import com.squareup.picasso.Picasso

class PicassoWrapper(private val picasso: Picasso,
                     private val imageUrlHelper: ImageUrlHelper) {
    fun fetchImage(imageId: Long) {
        picasso
                .load(imageUrlHelper.getImageUrl(imageId))
                .fetch()
    }
}