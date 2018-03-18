package ru.alexandrkutashov.onetimesecret.ext

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */

object OTPLink {

    const val BASE_URL = "https://onetimesecret.com"

    /**
     * Creates full link of the secret for sharing
     */
    fun secretLink(secretKey: String?): String? = secretKey?.let { "$BASE_URL/secret/$it" }

    /**
     * Retrieve secretKey from the shared secret link
     */
    fun secretKey(link: String): String? = link.split("/").last()
}