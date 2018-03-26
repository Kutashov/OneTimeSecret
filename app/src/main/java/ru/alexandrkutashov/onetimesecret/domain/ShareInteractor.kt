package ru.alexandrkutashov.onetimesecret.domain

import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareResponse
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

class ShareInteractor : AppInteractor() {

    suspend fun shareSecret(secret: String, passphrase: String? = null): Result<ShareResponse> =
            executeAsync {
                api.share(ShareRequest(
                        secret = secret,
                        passphrase = passphrase
                ))
            }
}