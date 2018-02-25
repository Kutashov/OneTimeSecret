package ru.alexandrkutashov.onetimesecret.repository

import com.github.kittinunf.fuel.Fuel.Companion.get
import com.github.kittinunf.fuel.Fuel.Companion.post
import com.github.kittinunf.fuel.core.FuelError
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.loggingResponseInterceptor
import com.github.kittinunf.fuel.moshi.responseObject
import com.github.kittinunf.result.Result
import ru.alexandrkutashov.onetimesecret.repository.model.*

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

interface OneTimeSecret {

    /**
     * Current status of the system.
     */
    fun status(): Pair<StatusResponse?, Exception?>

    /**
     * Stores a secret value.
     */
    fun share(shareRequest: ShareRequest): Pair<ShareResponse?, Exception?>

    /**
     * Generate a short, unique secret. This is useful for temporary passwords, one-time pads, salts, etc.
     */
    fun generate(generateRequest: GenerateRequest): Pair<GenerateResponse?, Exception?>

    /**
     * Retrieve a generated secret.
     */
    fun retrieve(retrieveRequest: RetrieveRequest): Pair<RetrieveResponse?, Exception?>

    /**
     * Every secret also has associated metadata. The metadata is intended to be used by the creator of the secret
     * (i.e. not the recipient) and should generally be kept private. You can safely use the metadata key to retrieve
     * basic information about the secret itself (e.g. if or when it was viewed) since the metadata key is different
     * from the secret key.
     */
    fun metadata(metadataRequest: MetadataRequest): Pair<MetadataResponse?, Exception?>

    /**
     * Burn a secret that has not been read yet.
     */
    fun burn(burnRequest: BurnRequest): Pair<BurnResponse?, Exception?>
}

class OneTimeSecretImpl : OneTimeSecret {

    val TAG = OneTimeSecretImpl::class.simpleName

    init {
        FuelManager.instance.apply {
            basePath = "https://onetimesecret.com/api"
            addResponseInterceptor { loggingResponseInterceptor() }
        }
    }

    var username: String? = null
    var apiToken: String? = null

    override fun status(): Pair<StatusResponse?, FuelError?> =
            get("v1/status")
                    .responseObject<StatusResponse>().third.toPair()

    override fun share(shareRequest: ShareRequest): Pair<ShareResponse?, FuelError?> =
            post("v1/share", listOf(
                    "secret" to shareRequest.secret,
                    "passphrase" to shareRequest.passphrase,
                    "ttl" to shareRequest.ttl,
                    "recipient" to shareRequest.recipient
            )).responseObject<ShareResponse>().third.toPair()

    override fun generate(generateRequest: GenerateRequest): Pair<GenerateResponse?, FuelError?> =
            post("v1/generate", listOf(
                    "passphrase" to generateRequest.passphrase,
                    "ttl" to generateRequest.ttl,
                    "metadata_ttl" to generateRequest.metadataTTL,
                    "secret_ttl" to generateRequest.secretTTL,
                    "recipient" to generateRequest.recipient
            )).responseObject<GenerateResponse>().third.toPair()

    override fun retrieve(retrieveRequest: RetrieveRequest): Pair<RetrieveResponse?, FuelError?> =
            post("v1/secret/${retrieveRequest.secretKey}", listOf(
                    "secret_key" to retrieveRequest.secretKey,
                    "passphrase" to retrieveRequest.passphrase
            )).responseObject<RetrieveResponse>().third.toPair()

    override fun metadata(metadataRequest: MetadataRequest): Pair<MetadataResponse?, FuelError?> =
            post("v1/private/${metadataRequest.metadataKey}", listOf(
                    "metadata_key" to metadataRequest.metadataKey
            )).responseObject<MetadataResponse>().third.toPair()

    override fun burn(burnRequest: BurnRequest): Pair<BurnResponse?, Exception?> =
            post("v1/private/${burnRequest.metadataKey}/burn")
                    .responseObject<BurnResponse>().third.toPair()

    private fun <T : Any, E : Exception> Result<T, E>.toPair(): Pair<T?, E?> = Pair(component1(), component2())
}

