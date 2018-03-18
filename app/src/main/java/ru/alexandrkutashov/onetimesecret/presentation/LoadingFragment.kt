package ru.alexandrkutashov.onetimesecret.presentation

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import ru.alexandrkutashov.onetimesecret.R

/**
 * Screen for long-term operations
 *
 * @author Alexandr Kutashov
 * @date 19.11.2017
 */

class LoadingFragment : Fragment() {

    companion object {
        val screenKey: String = LoadingFragment::class.simpleName!!
        fun newInstance(): Fragment = LoadingFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.loading_fragment,  container, false)
    }
}