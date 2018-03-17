package ru.alexandrkutashov.onetimesecret.domain

import kotlinx.coroutines.experimental.async
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareResponse
import ru.alexandrkutashov.onetimesecret.ext.Executors
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

class ShareInteractor : KoinComponent {

    private val executors by inject<Executors>()
    private val api by inject<OneTimeSecret>()

    suspend fun shareSecret(secret: String, passphrase: String? = null): Result<ShareResponse> =
            async(executors.networkContext) {
                api.share(ShareRequest(
                        secret = secret,
                        passphrase = passphrase
                ))
            }.await()

}