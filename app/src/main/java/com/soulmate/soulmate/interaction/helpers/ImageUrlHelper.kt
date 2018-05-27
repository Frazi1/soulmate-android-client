package com.soulmate.soulmate.interaction.helpers

import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.configuration.interfaces.IConnectionPreferenceManager
import com.soulmate.soulmate.interaction.api.ImageApi

class ImageUrlHelper(private val connectionPreferenceManager: IConnectionPreferenceManager) {
    fun getImageUrl(id: Long): String {
        return "${connectionPreferenceManager.serverUrl}${ImageApi.IMAGE_API_PATH}/$id"
    }

    fun getUserAvatarUrl(userAccountDto: UserAccountDto): String {
        val images = userAccountDto.profileImages
        if(images.isEmpty())
            return ""
        return getImageUrl(images.first().imageId)
    }
}