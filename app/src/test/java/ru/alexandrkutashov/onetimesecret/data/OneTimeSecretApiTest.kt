package ru.alexandrkutashov.onetimesecret.data

import com.github.kittinunf.fuel.core.FuelManager
import org.junit.Assert.*
import org.junit.BeforeClass
import org.junit.Test
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecretImpl
import ru.alexandrkutashov.onetimesecret.data.repository.model.*
import ru.alexandrkutashov.onetimesecret.ext.data
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

        val expected = ots.generate(generateRequest).data {
            fail(exception.toString())
        }!!

        val actual = ots.retrieve(
                RetrieveRequest(secretKey = expected.secretKey,
                        passphrase = generateRequest.passphrase)
        ).data {
            fail(exception.toString())
        }!!

        assertEquals(expected.value, actual.value)
    }

    @Test
    fun testShareRetrieve() {

        val shareRequest = ShareRequest(
                secret = "mooooo I'm a moooose",
                passphrase = "test-passhprase")

        val expected = ots.share(shareRequest).data {
            fail(exception.toString())
        }!!

        val actual = ots.retrieve(
                RetrieveRequest(
                        secretKey = expected.secretKey,
                        passphrase = shareRequest.passphrase)
        ).data {
            fail(exception.toString())
        }!!

        assertEquals(shareRequest.secret, actual.value)
    }

    @Test
    fun testGenerateMetadata() {

        val expected = ots.generate(GenerateRequest(
                passphrase = "ooga booga",
                metadataTTL = 111,
                secretTTL = 222,
                ttl = 333)).data {
            fail(exception.toString())
        }!!

        val actual = ots.metadata(MetadataRequest(
                metadataKey = expected.metadataKey)).data {
            fail(exception.toString())
        }!!

        assertEquals(actual.custId, expected.custId)
        assertEquals(actual.dateCreated, expected.dateCreated)
        assertEquals(actual.metadataKey, expected.metadataKey)
        assertEquals(actual.secretKey, expected.secretKey)
        assertEquals(actual.ttl, expected.ttl)
    }

    @Test
    fun testStatus() {

        val expected = StatusResponse(
                status = "nominal",
                locale = "en")

        val actual = ots.status().data {
            fail(exception.toString())
        }!!

        assertEquals(actual, expected)
    }

    @Test
    fun testGenerateBurn() {
        val shareRequest = ShareRequest(
                secret = "mooooo I'm a moooose")

        val expected = ots.share(shareRequest).data {
            fail(exception.toString())
        }!!

        val actual = ots.burn(
                BurnRequest(metadataKey = expected.metadataKey)
        ).data {
            fail(exception.toString())
        }!!

        assertEquals(actual.state.custId, expected.custId)
        assertEquals(actual.state.dateCreated, expected.dateCreated)
        assertEquals(actual.state.metadataKey, expected.metadataKey)
        assertEquals(actual.state.ttl, expected.ttl)
        assertTrue(actual.secretShortKey?.let { expected.secretKey?.startsWith(it) ?: false }
                ?: false)
    }
}
