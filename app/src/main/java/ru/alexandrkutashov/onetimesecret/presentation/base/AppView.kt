package ru.alexandrkutashov.onetimesecret.presentation.base

import com.arellomobile.mvp.MvpView

/**
 * Common view class
 *
 * @author Alexandr Kutashov
 * on 18.03.2018
 */
interface AppView: MvpView {

    /**
     * Shows or hide loading screen
     * @param flag show the screen if [flag] is true, hide it otherwise
     */
    fun showLoading(flag: Boolean)
}