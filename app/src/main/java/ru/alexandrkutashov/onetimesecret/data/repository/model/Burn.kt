package ru.alexandrkutashov.onetimesecret.data.repository.model

import com.squareup.moshi.Json

/**
 * @author Alexandr Kutashov
 * on 23.02.2018
 */

data class BurnRequest(
        val metadataKey: String? = null
)

data class BurnResponse(
        @Json(name = "state")
        val state: BurnState,
        @Json(name = "secret_shortkey")
        val secretShortKey: String?
)

data class BurnState(
        @Json(name = "custid")
        val custId: String? = null,
        @Json(name = "metadata_key")
        val metadataKey: String? = null,
        @Json(name = "secret_key")
        val secretKey: String? = null,
        @Json(name = "ttl")
        val ttl: Int? = null,
        @Json(name = "metadata_ttl")
        val metadataTtl: Int? = null,
        @Json(name = "secret_ttl")
        val secretTtl: Int? = null,
        @Json(name = "state")
        val state: String? = null,
        @Json(name = "updated")
        val dateUpdated: Long? = null,
        @Json(name = "created")
        val dateCreated: Long? = null,
        @Json(name = "recipient")
        val recipient: List<String>? = null
)