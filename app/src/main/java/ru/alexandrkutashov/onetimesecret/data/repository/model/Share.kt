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
        var custId: String? = null,
        @Json(name = "metadata_key")
        var metadataKey: String? = null,
        @Json(name = "secret_key")
        var secretKey: String? = null,
        @Json(name = "metadata_ttl")
        var metadataTtl: Int? = null,
        @Json(name = "secret_ttl")
        var secretTtl: Int? = null,
        @Json(name = "ttl")
        var ttl: Int? = null,
        @Json(name = "state")
        var state: String? = null,
        @Json(name = "created")
        var dateCreated: Long? = null,
        @Json(name = "updated")
        var dateUpdated: Long? = null,
        @Json(name = "passphrase_required")
        var isPassphraseRequired: Boolean = false
)