package ru.alexandrkutashov.onetimesecret.presentation

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentTransaction
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.android.synthetic.main.loading_layout.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.releaseContext
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.presentation.MainModule.Companion.MAIN
import ru.alexandrkutashov.onetimesecret.presentation.metadata.MetadataFragment
import ru.alexandrkutashov.onetimesecret.presentation.read.ReadFragment
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.*
import android.app.Activity
import android.view.inputmethod.InputMethodManager


/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class MainActivity : AppCompatActivity(), LoadingHandler, KeyboardHandler, FabHandler {

    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            val current = getCurrentFragment()
            when (current) {
                is FabListener -> (current as FabListener).onClick(view)
            }
        }

        if (intent.data != null) {
            router.newRootScreen(ReadFragment.screenKey, intent.data.toString())
        } else {
            router.newRootScreen(ShareFragment.screenKey, intent.getStringExtra(Intent.EXTRA_TEXT))
        }
    }

    override fun showLoading(flag: Boolean) {
        when (flag) {
            true -> loadingLayout.visibility = View.VISIBLE
            false -> loadingLayout.visibility = View.GONE
        }
    }

    override fun showKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInputFromInputMethod(toolbar.windowToken, 0)
    }

    override fun hideKeyboard() {
        val imm = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.hideSoftInputFromWindow(toolbar.windowToken, 0)
    }

    override fun showFab() {
        fab.visibility = View.VISIBLE
    }

    override fun hideFab() {
        fab.visibility = View.GONE
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (hasFocus) {
            window.setBackgroundDrawable(null)
        }
    }

    override fun onResume() {
        super.onResume()
        navigatorHolder.setNavigator(navigator)
    }

    override fun onPause() {
        super.onPause()
        navigatorHolder.removeNavigator()
    }

    override fun onDestroy() {
        super.onDestroy()
        releaseContext(MAIN)
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_settings -> true
            R.id.action_about -> {
                startActivity(Intent(this, AboutActivity::class.java))
                return true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun getCurrentFragment(): Fragment {
        val fragmentManager = supportFragmentManager
        val stackCount = fragmentManager.backStackEntryCount
        return fragmentManager.fragments[if (stackCount > 0) stackCount - 1 else stackCount]
    }

    private var navigator = object : SupportFragmentNavigator(supportFragmentManager,
            R.id.fragment) {
        override fun createFragment(screenKey: String?, data: Any?): Fragment = when (screenKey) {
            ShareFragment.screenKey -> ShareFragment.newInstance(data as String?)
            ReadFragment.screenKey -> ReadFragment.newInstance(data as String)
            MetadataFragment.screenKey -> MetadataFragment.newInstance(data as String)
            else -> throw RuntimeException("Unknown screen key!")
        }

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String?) {
            message?.let { Snackbar.make(toolbar, message, Snackbar.LENGTH_LONG).show() }
        }

        @SuppressLint("PrivateResource")
        override fun setupFragmentTransactionAnimation(command: Command?, currentFragment: Fragment?,
                                                       nextFragment: Fragment?,
                                                       fragmentTransaction: FragmentTransaction?) {
            if (command is Forward || command is Replace) {
                fragmentTransaction?.setCustomAnimations(R.anim.abc_fade_in, R.anim.abc_fade_out)
            } else if (command is Back || command is BackTo) {
                fragmentTransaction?.setCustomAnimations(R.anim.abc_fade_out, R.anim.abc_fade_in)
            }
        }
    }
}

interface LoadingHandler {
    fun showLoading(flag: Boolean)
}

interface KeyboardHandler {
    fun showKeyboard()
    fun hideKeyboard()
}

interface FabHandler {
    fun showFab()
    fun hideFab()
}

interface FabListener {
    fun onClick(view: View)
}