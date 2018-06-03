package ru.alexandrkutashov.onetimesecret.ext

import android.content.ClipboardManager
import android.content.Context
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.RuntimeEnvironment

/**
 * Test for [OTPLink]
 *
 * @author Alexandr Kutashov
 * on 17.03.2018
 */
@Suppress("DEPRECATION")
@RunWith(RobolectricTestRunner::class)
class LinksTest {

    companion object {
        private val URL = "https://onetimesecret.com"
        private val SECRET_KEY = "fennib70i6eunsep0w1nh9riwq0r7sw"
        private val TEST_CONTENT = "someTestContent"
    }

    @Test
    fun createSecretLink() {

        val secretKey = "someSecretKey"
        val expected = "$URL/secret/$secretKey"

        val actual = OTPLink.secretLink(secretKey)
        assertEquals(expected, actual)
    }

    @Test
    fun createSecretKey() {
        val link = "$URL/secret/$SECRET_KEY"
        val expected = SECRET_KEY

        val actual = OTPLink.secretKey(link)
        assertEquals(expected, actual)
    }

    @Test
    fun copyLink() {
        val clipboardManager = RuntimeEnvironment.application.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager

        OTPLink.copyLink(RuntimeEnvironment.application, TEST_CONTENT)
        assertTrue(clipboardManager.text == TEST_CONTENT)
    }
}