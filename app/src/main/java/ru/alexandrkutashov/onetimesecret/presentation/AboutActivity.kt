package ru.alexandrkutashov.onetimesecret.presentation

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.webkit.WebView
import ru.alexandrkutashov.onetimesecret.R
import ru.alexandrkutashov.onetimesecret.ext.OTPLink

/**
 * @author Alexandr Kutashov
 * on 18.03.2018
 */
class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.about_activity)

        findViewById<WebView>(R.id.about)?.loadUrl("${OTPLink.BASE_URL}/about")
    }
}