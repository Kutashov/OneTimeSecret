package ru.alexandrkutashov.onetimesecret.presentation.share

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * View for [ShareFragment]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface ShareView: MvpView {

    /**
     * Secret was successfully shared
     * @param link Url for sharing
     */
    fun onShareSuccess(link: String?)

    /**
     * Some error happened during sharing
     * @param message some info about error
     */
    fun onShareError(message: String?)
}