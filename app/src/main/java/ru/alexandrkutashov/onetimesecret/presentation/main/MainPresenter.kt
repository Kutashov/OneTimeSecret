package ru.alexandrkutashov.onetimesecret.presentation.main

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import kotlinx.coroutines.experimental.launch
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import org.koin.standalone.releaseContext
import ru.alexandrkutashov.onetimesecret.domain.MainInteractor
import ru.alexandrkutashov.onetimesecret.ext.Executors
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.presentation.main.MainModule.Companion.MAIN

/**
 * Presenter for [MainView]
 *
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

@InjectViewState
class MainPresenter : MvpPresenter<MainView>(), KoinComponent {

    private val executors by inject<Executors>()
    private val interactor by inject<MainInteractor>()

    override fun onFirstViewAttach() {
        super.onFirstViewAttach()
    }

    fun shareSecret(secret: String, passphrase: String) = launch(executors.uiContext) {

        if (secret.isEmpty()) {

        } else {
            viewState.showLoading(true)

            val result = interactor.shareSecret(secret, passphrase)
            when(result) {
                is Result.Success -> ""
                is Result.Error -> result.exception
            }
            viewState.showLoading(false)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseContext(MAIN)
    }
}