package ru.alexandrkutashov.onetimesecret.presentation.share

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.launch
import org.koin.standalone.inject
import org.koin.standalone.releaseContext
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.domain.ShareInteractor
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.secretLink
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.ext.log
import ru.alexandrkutashov.onetimesecret.presentation.AppPresenter
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

    override fun screenKey(): String = ShareFragment.screenKey

    fun shareSecret(secret: String, passphrase: String? = null) = launch(executors.uiContext) {

        if (secret.isEmpty()) {
            router.showSystemMessage(resourceManager.getString(R.string.empty_secret))
        } else {
            showLoading(true)

            val result = interactor.shareSecret(secret, passphrase)
            when (result) {
                is Result.Success -> viewState.onShareSuccess(secretLink(result.data.secretKey))
                is Result.Error -> {
                    result.exception.log()
                    viewState.onShareError(result.exception.message)
                }
            }

            showLoading(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseContext(SHARE)
    }

}