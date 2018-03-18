package ru.alexandrkutashov.onetimesecret.presentation.read

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * View for [ReadFragment]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface ReadView: MvpView {

    /**
     * Secret was successfully read
     * @param link Url for sharing
     */
    fun onReadSuccess(value: String?)

    /**
     * Some error happened during reading
     * @param message some info about error
     */
    fun onReadError(message: String?)
}