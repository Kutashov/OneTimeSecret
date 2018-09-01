package ru.alexandrkutashov.onetimesecret.domain

import io.mockk.every
import io.mockk.verify
import org.junit.Assert.assertEquals
import kotlinx.coroutines.experimental.runBlocking
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import ru.alexandrkutashov.onetimesecret.TestAppModule
import ru.alexandrkutashov.onetimesecret.data.TestDataModule
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.data.repository.model.*
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * Test for [MetadataInteractor]
 *
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
class MetadataInteractorTest : KoinTest {

    private val api by inject<OneTimeSecret>()
    private lateinit var interactor: MetadataInteractor

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDomainModule(), TestDataModule()))
        interactor = MetadataInteractor()
    }

    @Test
    fun metadata() = runBlocking {
        val metadataKey = "someMetadataKey"
        val expected = Result.Success(MetadataResponse(
                metadataKey = "someMetadataKey",
                secretKey = "someSecretKey"
        ))

        every { api.metadata(any()) } answers { expected }

        val actual = interactor.getSecretMetadata(metadataKey)

        verify { api.metadata(MetadataRequest(metadataKey = metadataKey)) }
        assertEquals(actual, expected)
    }

    @Test
    fun burn() = runBlocking {
        val metadataKey = "someMetadataKey"
        val expected = Result.Success(BurnResponse(
                state = BurnState(state = "burned"),
                secretShortKey = "secretKey"
        ))

        every { api.burn(any()) } answers { expected }

        val actual = interactor.burnSecret(metadataKey)

        verify { api.burn(BurnRequest(metadataKey = metadataKey)) }
        assertEquals(actual, expected)
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}