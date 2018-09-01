package ru.alexandrkutashov.onetimesecret.presentation.metadata

import com.arellomobile.mvp.viewstate.strategy.OneExecutionStateStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.alexandrkutashov.onetimesecret.presentation.base.AppView

/**
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
@StateStrategyType(OneExecutionStateStrategy::class)
interface MetadataView : AppView {

    /**
     * Metadata was successfully obtained
     * @param secretLink link for sharing to others
     * @param isPassphraseRequired flag if the secret was supplied with a password
     * @param inTimeString string saying how much time left
     */
    fun onMetadataSuccess(secretLink: String?, isPassphraseRequired: Boolean, inTimeString: String?)

    /**
     * Some error happened during request
     * @param message some info about error
     */
    fun onError(message: String?)

    /**
     * Secret was burned
     */
    fun onBurnSecretSuccess()
}