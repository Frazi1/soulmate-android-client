package com.soulmate.soulmate.presentation.activity.chat

import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.interaction.helpers.ImageUrlHelper
import com.stfalcon.chatkit.commons.models.IUser


class Author(private val userAccountDto: UserAccountDto,
             private val urlHelper: ImageUrlHelper) : IUser {

    override fun getId(): String = userAccountDto.id.toString()
    override fun getAvatar(): String = urlHelper.getUserAvatarUrl(userAccountDto)
    override fun getName(): String {
        return "${userAccountDto.lastName} ${userAccountDto.firstName}"
    }
}