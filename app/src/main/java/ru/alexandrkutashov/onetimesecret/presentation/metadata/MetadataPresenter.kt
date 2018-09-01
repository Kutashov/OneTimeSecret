package ru.alexandrkutashov.onetimesecret.presentation.metadata

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.launch
import org.koin.standalone.inject
import org.koin.standalone.releaseContext
import ru.alexandrkutashov.onetimesecret.domain.MetadataInteractor
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.secretLink
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.ext.log
import ru.alexandrkutashov.onetimesecret.ext.secondsToInTimeString
import ru.alexandrkutashov.onetimesecret.presentation.base.AppPresenter
import ru.alexandrkutashov.onetimesecret.presentation.metadata.MetadataModule.Companion.METADATA

/**
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
@InjectViewState
class MetadataPresenter : AppPresenter<MetadataView>() {

    private val interactor by inject<MetadataInteractor>()

    fun getSecretMetadata(metadataKey: String?) = launch(executors.uiContext) {

        viewState.showLoading(true)

        val result = interactor.getSecretMetadata(metadataKey)
        when (result) {
            is Result.Success -> viewState.onMetadataSuccess(secretLink(result.data.secretKey),
                    result.data.isPassphraseRequired,
                    result.data.secretTtl.secondsToInTimeString(resourceManager))
            is Result.Error -> {
                result.exception.log()
                viewState.onError(result.exception.message)
            }
        }

        viewState.showLoading(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.cancelJobs()
        releaseContext(METADATA)
    }

    fun burnSecret(metadataKey: String?) = launch(executors.uiContext) {
        viewState.showLoading(true)

        val result = interactor.burnSecret(metadataKey)
        when (result) {
            is Result.Success -> viewState.onBurnSecretSuccess()
            is Result.Error -> {
                result.exception.log()
                viewState.onError(result.exception.message)
            }
        }

        viewState.showLoading(false)
    }

}