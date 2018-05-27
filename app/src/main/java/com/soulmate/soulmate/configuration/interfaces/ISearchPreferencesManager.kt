package com.soulmate.soulmate.configuration.interfaces

import com.soulmate.shared.GenderType

interface ISearchPreferencesManager {
    var minAge: Int
    var maxAge: Int
    var showMale: Boolean
    var showFemale: Boolean
    val genderTypes: List<GenderType>
}