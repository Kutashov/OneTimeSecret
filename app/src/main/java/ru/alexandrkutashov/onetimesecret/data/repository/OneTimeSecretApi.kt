package ru.alexandrkutashov.onetimesecret.data.repository

import com.github.kittinunf.fuel.Fuel.Companion.get
import com.github.kittinunf.fuel.Fuel.Companion.post
import com.github.kittinunf.fuel.core.FuelManager
import com.github.kittinunf.fuel.core.interceptors.loggingResponseInterceptor
import com.github.kittinunf.fuel.moshi.responseObject
import org.koin.standalone.KoinComponent
import ru.alexandrkutashov.onetimesecret.data.repository.model.*
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * @author Alexandr Kutashov
 *         on 23.02.2018
 */

interface OneTimeSecret {

    /**
     * Current status of the system.
     */
    fun status(): Result<StatusResponse>

    /**
     * Stores a secret value.
     */
    fun share(shareRequest: ShareRequest): Result<ShareResponse>

    /**
     * Generate a short, unique secret. This is useful for temporary passwords, one-time pads, salts, etc.
     */
    fun generate(generateRequest: GenerateRequest): Result<GenerateResponse>

    /**
     * Retrieve a generated secret.
     */
    fun retrieve(retrieveRequest: RetrieveRequest): Result<RetrieveResponse>

    /**
     * Every secret also has associated metadata. The metadata is intended to be used by the creator of the secret
     * (i.e. not the recipient) and should generally be kept private. You can safely use the metadata key to retrieve
     * basic information about the secret itself (e.g. if or when it was viewed) since the metadata key is different
     * from the secret key.
     */
    fun metadata(metadataRequest: MetadataRequest): Result<MetadataResponse>

    /**
     * Burn a secret that has not been read yet.
     */
    fun burn(burnRequest: BurnRequest): Result<BurnResponse>
}

class OneTimeSecretImpl : OneTimeSecret, KoinComponent {

    var username: String? = null
    var apiToken: String? = null

    init {
        FuelManager.instance.apply {
            basePath = "https://onetimesecret.com/api"
            addResponseInterceptor { loggingResponseInterceptor() }
        }
    }

    override fun status(): Result<StatusResponse> =
            get("v1/status")
                    .responseObject<StatusResponse>().third.toPair()

    override fun share(shareRequest: ShareRequest): Result<ShareResponse> =
            post("v1/share", listOf(
                    "secret" to shareRequest.secret,
                    "passphrase" to shareRequest.passphrase,
                    "ttl" to shareRequest.ttl,
                    "recipient" to shareRequest.recipient
            )).responseObject<ShareResponse>().third.toPair()

    override fun generate(generateRequest: GenerateRequest): Result<GenerateResponse> =
            post("v1/generate", listOf(
                    "passphrase" to generateRequest.passphrase,
                    "ttl" to generateRequest.ttl,
                    "metadata_ttl" to generateRequest.metadataTTL,
                    "secret_ttl" to generateRequest.secretTTL,
                    "recipient" to generateRequest.recipient
            )).responseObject<GenerateResponse>().third.toPair()

    override fun retrieve(retrieveRequest: RetrieveRequest): Result<RetrieveResponse> =
            post("v1/secret/${retrieveRequest.secretKey}", listOf(
                    "secret_key" to retrieveRequest.secretKey,
                    "passphrase" to retrieveRequest.passphrase
            )).responseObject<RetrieveResponse>().third.toPair()

    override fun metadata(metadataRequest: MetadataRequest): Result<MetadataResponse> =
            post("v1/private/${metadataRequest.metadataKey}", listOf(
                    "metadata_key" to metadataRequest.metadataKey
            )).responseObject<MetadataResponse>().third.toPair()

    override fun burn(burnRequest: BurnRequest): Result<BurnResponse> =
            post("v1/private/${burnRequest.metadataKey}/burn")
                    .responseObject<BurnResponse>().third.toPair()

    private fun <T : Any, E : Exception> com.github.kittinunf.result.Result<T, E>.toPair(): Result<T> = when {
        component1() != null -> Result.Success(component1()!!)
        component2() != null -> Result.Error(component2()!!)
        else -> Result.Error(Exception())
    }
}

