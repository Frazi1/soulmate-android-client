package com.soulmate.soulmate.configuration

import com.soulmate.shared.dtos.UserAccountDto
import com.soulmate.soulmate.configuration.interfaces.IUserContexHolder

class UserContextHolder : IUserContexHolder {
    override var user: UserAccountDto? = null
}