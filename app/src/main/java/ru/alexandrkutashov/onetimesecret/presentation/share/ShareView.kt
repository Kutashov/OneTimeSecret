package ru.alexandrkutashov.onetimesecret.presentation.share

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.alexandrkutashov.onetimesecret.presentation.base.AppView

/**
 * View for [ShareFragment]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@StateStrategyType(OneExecutionStateStrategy::class)
interface ShareView : AppView {

    /**
     * Secret was successfully shared
     * @param link Url for sharing
     * @param metadataKey A private key for accessing secret's info
     */
    fun onShareSuccess(link: String?, metadataKey: String?)

    /**
     * Some error happened during sharing
     * @param message some info about error
     */
    fun onShareError(message: String?)
}