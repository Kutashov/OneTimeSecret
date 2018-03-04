package ru.alexandrkutashov.onetimesecret.data.repository.model

import com.squareup.moshi.Json

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */
data class StatusResponse(
        @Json(name = "status")
        var status: String? = null,
        @Json(name = "locale")
        var locale: String? = null
)