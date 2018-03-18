package ru.alexandrkutashov.onetimesecret.domain

import kotlinx.coroutines.experimental.async
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveResponse
import ru.alexandrkutashov.onetimesecret.ext.Executors
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */
class ReadInteractor : KoinComponent {

    private val executors by inject<Executors>()
    private val api by inject<OneTimeSecret>()

    suspend fun readSecret(secretKey: String?, passphrase: String? = null): Result<RetrieveResponse> =
            async(executors.networkContext) {
                api.retrieve(RetrieveRequest(
                        secretKey = secretKey,
                        passphrase = passphrase
                ))
            }.await()
}