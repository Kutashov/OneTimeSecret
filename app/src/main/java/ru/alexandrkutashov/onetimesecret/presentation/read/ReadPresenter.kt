package ru.alexandrkutashov.onetimesecret.presentation.read

import com.arellomobile.mvp.InjectViewState
import kotlinx.coroutines.experimental.launch
import org.koin.standalone.inject
import org.koin.standalone.releaseContext
import ru.alexandrkutashov.onetimesecret.domain.ReadInteractor
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.secretKey
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.ext.log
import ru.alexandrkutashov.onetimesecret.presentation.base.AppPresenter
import ru.alexandrkutashov.onetimesecret.presentation.read.ReadModule.Companion.READ

/**
 * Presenter for [ReadView]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@InjectViewState
class ReadPresenter : AppPresenter<ReadView>() {

    private val interactor by inject<ReadInteractor>()

    fun readSecret(link: String, passphrase: String? = null) = launch(executors.uiContext) {

        viewState.showLoading(true)

        val result = interactor.readSecret(secretKey(link), passphrase)
        when (result) {
            is Result.Success -> viewState.onReadSuccess(result.data.value)
            is Result.Error -> {
                result.exception.log()
                viewState.onReadError(result.exception.message)
            }
        }

        viewState.showLoading(false)
    }

    override fun onDestroy() {
        super.onDestroy()
        interactor.cancelJobs()
        releaseContext(READ)
    }

}