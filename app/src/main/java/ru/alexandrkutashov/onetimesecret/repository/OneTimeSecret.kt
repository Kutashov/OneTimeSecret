package ru.alexandrkutashov.onetimesecret.repository

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
}