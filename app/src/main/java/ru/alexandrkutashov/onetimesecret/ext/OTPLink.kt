package ru.alexandrkutashov.onetimesecret.ext

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context

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

    /**
     * Copy link to the clipboard
     */
    fun copyLink(context: Context, link: String) {
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("Secret for sharing", link)
        clipboard.primaryClip = clip
    }
}