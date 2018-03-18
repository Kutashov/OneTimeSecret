package ru.alexandrkutashov.onetimesecret.presentation.read

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.alexandrkutashov.onetimesecret.presentation.base.AppView

/**
 * View for [ReadFragment]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@StateStrategyType(OneExecutionStateStrategy::class)
interface ReadView: AppView {

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