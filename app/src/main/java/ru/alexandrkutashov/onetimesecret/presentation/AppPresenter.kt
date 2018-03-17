package ru.alexandrkutashov.onetimesecret.presentation

import android.content.res.Resources
import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.alexandrkutashov.onetimesecret.ext.Executors
import ru.terrakok.cicerone.Router

/**
 * Common presenter class
 *
 * @author Alexandr Kutashov
 * on 17.03.2018
 */
abstract class AppPresenter<T : MvpView> : MvpPresenter<T>(), KoinComponent {

    protected val executors by inject<Executors>()
    protected val resourceManager by inject<Resources>()
    protected val router by inject<Router>()

    protected abstract fun screenKey(): String

    protected fun showLoading(flag: Boolean) {
        when (flag) {
            true -> router.navigateTo(LoadingFragment.screenKey)
            false -> router.backTo(screenKey())
        }
    }
}