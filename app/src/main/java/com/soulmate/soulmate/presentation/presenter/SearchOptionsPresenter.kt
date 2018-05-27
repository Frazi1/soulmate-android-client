package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.LazyKodein
import com.github.salomonbrys.kodein.instance
import com.soulmate.shared.GenderType
import com.soulmate.soulmate.configuration.interfaces.ISearchPreferencesManager
import com.soulmate.soulmate.presentation.view.ISearchOptionsView

@InjectViewState
class SearchOptionsPresenter(lazyKodein: LazyKodein) : BasePresenter<ISearchOptionsView>(lazyKodein) {
    private val searchPreferencesManager: ISearchPreferencesManager by instance()

    var minAge: Int
        get() = searchPreferencesManager.minAge
        set(value) {
            searchPreferencesManager.minAge = value
        }

    var maxAge: Int
        get() = searchPreferencesManager.maxAge
        set(value) {
            searchPreferencesManager.maxAge = value
        }

    var showMale: Boolean
        get() = searchPreferencesManager.showMale
        set(value) {
            searchPreferencesManager.showMale = value
        }

    var showFemale: Boolean
        get() = searchPreferencesManager.showFemale
        set(value) {
            searchPreferencesManager.showFemale = value
        }

//    var gender: List<GenderType>
//        get() = searchPreferencesManager.gender
//        set(value) {
//            searchPreferencesManager.gender = value
//        }
}
