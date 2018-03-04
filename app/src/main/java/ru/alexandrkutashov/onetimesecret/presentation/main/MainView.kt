package ru.alexandrkutashov.onetimesecret.presentation.main

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * View for [MainFragment]
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@StateStrategyType(OneExecutionStateStrategy::class)
interface MainView: MvpView {

    /**
     * Show loading animation
     * @param flag if true than animation will appear
     */
    @StateStrategyType(AddToEndSingleStrategy::class)
    fun showLoading(flag: Boolean)

    /**
     * Secret was successfully shared
     */
    fun onShareSuccess()

    /**
     * Some error happened during error
     * @param message some info about error
     */
    fun onShareError(message: String?)

    /**
     * Shared secret was empty
     */
    fun onEmptySecret()

}