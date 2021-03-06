package ru.alexandrkutashov.onetimesecret.data.repository.model

import com.squareup.moshi.Json

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */
data class RetrieveRequest (
    val secretKey: String? = null,
    val passphrase: String? = null
)

data class RetrieveResponse(
        @Json(name = "value")
        val value: String? = null,
        @Json(name = "secret_key")
        val secretKey: String? = null
)