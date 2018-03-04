package ru.alexandrkutashov.onetimesecret.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * View for [MainFragment]
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface MainView: MvpView {

    fun showLoading(flag: Boolean)
}