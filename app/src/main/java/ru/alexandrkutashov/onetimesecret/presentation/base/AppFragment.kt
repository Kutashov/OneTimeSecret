package ru.alexandrkutashov.onetimesecret.presentation.base

import com.arellomobile.mvp.MvpAppCompatFragment
import org.koin.android.ext.android.inject
import ru.alexandrkutashov.onetimesecret.presentation.FabHandler
import ru.alexandrkutashov.onetimesecret.presentation.KeyboardHandler
import ru.alexandrkutashov.onetimesecret.presentation.LoadingHandler
import ru.terrakok.cicerone.Router

/**
 * Common fragment class
 *
 * @author Alexandr Kutashov
 * on 19.03.2018
 */
open class AppFragment : MvpAppCompatFragment(), AppView {

    protected val router by inject<Router>()

    override fun showLoading(flag: Boolean) {
        (activity as? LoadingHandler)?.showLoading(flag)
    }

    protected fun showKeyboard() {
        (activity as? KeyboardHandler)?.showKeyboard()
    }

    protected fun hideKeyboard() {
        (activity as? KeyboardHandler)?.hideKeyboard()
    }

    protected fun showFab() {
        (activity as? FabHandler)?.showFab()
    }

    protected fun hideFab() {
        (activity as? FabHandler)?.hideFab()
    }
}