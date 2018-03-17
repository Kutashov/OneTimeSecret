package ru.alexandrkutashov.onetimesecret.ext

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */

object OTPLink {

    val baseUrl = "https://onetimesecret.com"

    fun secretLink(secretKey: String?): String? = secretKey?.let { "$baseUrl/secret/$it" }
}