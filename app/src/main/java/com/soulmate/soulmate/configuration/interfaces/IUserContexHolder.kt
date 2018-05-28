package com.soulmate.soulmate.configuration.interfaces

import com.soulmate.shared.dtos.UserAccountDto

interface IUserContexHolder {
    var user: UserAccountDto?
}