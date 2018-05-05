package com.soulmate.soulmate.presentation.presenter

import com.arellomobile.mvp.InjectViewState
import com.github.salomonbrys.kodein.LazyKodein
import com.soulmate.soulmate.presentation.view.ISearchOptionsView

@InjectViewState
class SearchOptionsPresenter(lazyKodein: LazyKodein) : BasePresenter<ISearchOptionsView>(lazyKodein) {

}
