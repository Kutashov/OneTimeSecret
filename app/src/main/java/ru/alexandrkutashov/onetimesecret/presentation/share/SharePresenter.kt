package ru.alexandrkutashov.onetimesecret.presentation.share

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.standalone.inject
import org.koin.standalone.releaseContext
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.domain.ShareInteractor
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.secretLink
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.ext.log
import ru.alexandrkutashov.onetimesecret.presentation.base.AppPresenter
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareModule.Companion.SHARE

/**
 * Presenter for [ShareView]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@InjectViewState
class SharePresenter : AppPresenter<ShareView>() {

    private val interactor by inject<ShareInteractor>()

    fun shareSecret(secret: String, passphrase: String? = null) = GlobalScope.launch(executors.uiContext) {

        if (secret.isEmpty()) {
            viewState.onShareError(resourceManager.getString(R.string.empty_secret))
        } else {
            viewState.showLoading(true)

            val result = interactor.shareSecret(secret, passphrase)
            when (result) {
                is Result.Success -> viewState.onShareSuccess(secretLink(result.data.secretKey),
                        result.data.metadataKey)
                is Result.Error -> {
                    result.exception.log()
                    viewState.onShareError(result.exception.message)
                }
            }

            viewState.showLoading(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.cancelJobs()
        releaseContext(SHARE)
    }

}