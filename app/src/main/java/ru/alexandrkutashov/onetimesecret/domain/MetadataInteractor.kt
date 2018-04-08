package ru.alexandrkutashov.onetimesecret.domain

import ru.alexandrkutashov.onetimesecret.data.repository.model.MetadataRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.MetadataResponse
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
class MetadataInteractor : AppInteractor() {

    suspend fun getSecretMetadata(metadataKey: String?): Result<MetadataResponse> =
            executeAsync {
                api.metadata(MetadataRequest(
                        metadataKey = metadataKey
                ))
            }
}