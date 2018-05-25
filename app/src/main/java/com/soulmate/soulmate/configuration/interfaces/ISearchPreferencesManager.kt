package com.soulmate.soulmate.configuration.interfaces

import com.soulmate.shared.GenderType

interface ISearchPreferencesManager {
    var minAge: Int
    var maxAge: Int
    val genderTypes: List<GenderType>
}