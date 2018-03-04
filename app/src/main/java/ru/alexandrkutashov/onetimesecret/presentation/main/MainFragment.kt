package ru.alexandrkutashov.onetimesecret.presentation.main

import android.support.v4.app.Fragment
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.alexandrkutashov.onetimesecret.R

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class MainFragment : Fragment(), MainView {

    @InjectPresenter
    lateinit var presenter: MainPresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_main, container, false)
    }

    override fun showLoading(flag: Boolean) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
