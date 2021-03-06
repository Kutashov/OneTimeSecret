package ru.alexandrkutashov.onetimesecret.data.repository.model

import com.squareup.moshi.Json

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

data class ShareRequest(
        val secret: String? = null,
        val passphrase: String? = null,
        val ttl: Int? = null,
        val recipient: String? = null
)

data class ShareResponse(
        @Json(name = "custid")
        val custId: String? = null,
        @Json(name = "metadata_key")
        val metadataKey: String? = null,
        @Json(name = "secret_key")
        val secretKey: String? = null,
        @Json(name = "metadata_ttl")
        val metadataTtl: Int? = null,
        @Json(name = "secret_ttl")
        val secretTtl: Int? = null,
        @Json(name = "ttl")
        val ttl: Int? = null,
        @Json(name = "state")
        val state: String? = null,
        @Json(name = "created")
        val dateCreated: Long? = null,
        @Json(name = "updated")
        val dateUpdated: Long? = null,
        @Json(name = "passphrase_required")
        val isPassphraseRequired: Boolean = false
)