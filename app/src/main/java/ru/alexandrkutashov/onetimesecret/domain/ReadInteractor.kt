package ru.alexandrkutashov.onetimesecret.domain

import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveResponse
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */
class ReadInteractor : AppInteractor() {

    suspend fun readSecret(secretKey: String?, passphrase: String? = null): Result<RetrieveResponse> =
            executeAsync {
                api.retrieve(RetrieveRequest(
                        secretKey = secretKey,
                        passphrase = passphrase
                ))
            }
}