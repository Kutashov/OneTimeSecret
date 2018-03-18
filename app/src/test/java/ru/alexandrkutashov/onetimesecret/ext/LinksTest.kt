package ru.alexandrkutashov.onetimesecret.ext

import junit.framework.Assert.assertEquals
import org.junit.Test

/**
 * Test for [OTPLink]
 *
 * @author Alexandr Kutashov
 * on 17.03.2018
 */

class LinksTest {

    companion object {
        private val URL = "https://onetimesecret.com"
        private val SECRET_KEY = "fennib70i6eunsep0w1nh9riwq0r7sw"
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
}