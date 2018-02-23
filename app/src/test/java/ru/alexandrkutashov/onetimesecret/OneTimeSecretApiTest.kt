package ru.alexandrkutashov.onetimesecret

import com.github.kittinunf.fuel.core.FuelManager
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.BeforeClass
import org.junit.Test
import ru.alexandrkutashov.onetimesecret.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.repository.OneTimeSecretImpl
import ru.alexandrkutashov.onetimesecret.repository.model.*
import java.util.concurrent.Executor


/**
 * Test for [OneTimeSecret]
 *
 * @author Alexandr Kutashov
 *
 */

class OneTimeSecretApiTest {

    init {
        FuelManager.instance.apply {
            callbackExecutor = Executor(Runnable::run)
        }
    }

    companion object {
        private lateinit var ots: OneTimeSecret

        @BeforeClass
        @JvmStatic
        fun setUp() {
            ots = OneTimeSecretImpl()
        }
    }

    @Test
    fun testGenerateRetrieve() {

        val generateRequest = GenerateRequest(passphrase = "test-passphrase")

        val (expected, expectedError) = ots.generate(generateRequest)
        assertNull(expectedError)

        val (actual, actualError) = ots.retrieve(
                RetrieveRequest(secretKey = expected?.secretKey,
                        passphrase = generateRequest.passphrase)
        )
        assertNull(actualError)

        assertEquals(expected?.value, actual?.value)
    }

    @Test
    fun testShareRetrieve() {

        val shareRequest = ShareRequest(
                secret = "mooooo I'm a moooose",
                passphrase = "test-passhprase")

        val (expected, expectedError) = ots.share(shareRequest)
        assertNull(expectedError)

        val (actual, actualError) = ots.retrieve(
                RetrieveRequest(
                        secretKey = expected?.secretKey,
                        passphrase = shareRequest.passphrase)
        )
        assertNull(actualError)

        assertEquals(shareRequest.secret, actual?.value)
    }

    @Test
    fun testGenerateMetadata() {

        val (expected, expectedError) = ots.generate(GenerateRequest(
                passphrase = "ooga booga",
                metadataTTL = 111,
                secretTTL = 222,
                ttl = 333))
        assertNull(expectedError)

        val (actual, actualError) = ots.metadata(MetadataRequest(
                metadataKey = expected?.metadataKey))
        assertNull(actualError)

        assertEquals(actual?.custId, expected?.custId)
        assertEquals(actual?.dateCreated, expected?.dateCreated)
        assertEquals(actual?.metadataKey, expected?.metadataKey)
        assertEquals(actual?.secretKey, expected?.secretKey)
        assertEquals(actual?.ttl, expected?.ttl)
    }

    @Test
    fun testStatus() {

        val expected = StatusResponse(
                status = "nominal",
                locale = "en")

        val (actual, actualError) = ots.status()
        assertNull(actualError)

        assertEquals(actual, expected)
    }
}
