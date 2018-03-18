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
import kotlinx.android.synthetic.main.activity_main.*
import org.koin.android.ext.android.inject
import org.koin.android.ext.android.releaseContext
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.ext.toast
import ru.alexandrkutashov.onetimesecret.presentation.MainModule.Companion.MAIN
import ru.alexandrkutashov.onetimesecret.presentation.read.ReadFragment
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareFragment
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router
import ru.terrakok.cicerone.android.SupportFragmentNavigator
import ru.terrakok.cicerone.commands.*

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

class MainActivity : AppCompatActivity() {

    private val navigatorHolder by inject<NavigatorHolder>()
    private val router by inject<Router>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        fab.setOnClickListener { view ->
            Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                    .setAction("Action", null).show()
        }

        if (intent.data != null) {
            router.newRootScreen(ReadFragment.screenKey, intent.data.toString())
        } else {
            router.newRootScreen(ShareFragment.screenKey)
        }
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

    private var navigator = object : SupportFragmentNavigator(supportFragmentManager,
            R.id.fragment) {
        override fun createFragment(screenKey: String?, data: Any?): Fragment = when (screenKey) {
            LoadingFragment.screenKey -> LoadingFragment.newInstance()
            ShareFragment.screenKey -> ShareFragment.newInstance()
            ReadFragment.screenKey -> ReadFragment.newInstance(data as String)
            else -> throw RuntimeException("Unknown screen key!")
        }

        override fun exit() {
            finish()
        }

        override fun showSystemMessage(message: String?) {
            message?.let { toast(message) }
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
