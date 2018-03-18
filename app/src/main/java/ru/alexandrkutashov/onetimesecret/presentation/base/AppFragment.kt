package ru.alexandrkutashov.onetimesecret.presentation.base

import com.arellomobile.mvp.MvpAppCompatFragment
import ru.alexandrkutashov.onetimesecret.presentation.LoadingHandler

/**
 * @author Alexandr Kutashov
 * on 19.03.2018
 */
open class AppFragment : MvpAppCompatFragment(), AppView {

    override fun showLoading(flag: Boolean) {
        (activity as LoadingHandler).showLoading(flag)
    }
}