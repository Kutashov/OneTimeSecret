package ru.alexandrkutashov.onetimesecret.presentation.base

import android.content.res.Resources
import com.arellomobile.mvp.MvpPresenter
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.alexandrkutashov.onetimesecret.ext.Executors

/**
 * Common presenter class
 *
 * @author Alexandr Kutashov
 * on 17.03.2018
 */
abstract class AppPresenter<T : AppView> : MvpPresenter<T>(), KoinComponent {

    protected val executors by inject<Executors>()
    protected val resourceManager by inject<Resources>()
}