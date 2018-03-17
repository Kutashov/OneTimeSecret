package ru.alexandrkutashov.onetimesecret.ext

import junit.framework.Assert.assertEquals
import org.junit.Assert
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
    }

    @Test
    fun createSecretLink() {

        val secretKey = "someSecretKey"
        val expected = "$URL/secret/$secretKey"

        val actual = OTPLink.secretLink(secretKey)
        assertEquals(actual, expected)
    }
}