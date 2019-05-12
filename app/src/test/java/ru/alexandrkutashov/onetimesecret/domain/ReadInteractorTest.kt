package ru.alexandrkutashov.onetimesecret.domain

import io.mockk.every
import io.mockk.verify
import org.junit.Assert.assertEquals
import kotlinx.coroutines.runBlocking
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
import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveResponse
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * Test for [ReadInteractor]
 *
 * @author Alexandr Kutashov
 * on 18.03.2018
 */
class ReadInteractorTest : KoinTest {

    private val api by inject<OneTimeSecret>()
    private lateinit var interactor: ReadInteractor

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDomainModule(), TestDataModule()))
        interactor = ReadInteractor()
    }

    @Test
    fun read() = runBlocking {
        val secretKey = "someSecretKey"
        val passphrase = "somePassphrase"
        val expected = Result.Success(RetrieveResponse(
                value = "some text of secret",
                secretKey = "someSecret"
        ))

        every { api.retrieve(any()) } answers { expected }

        val actual = interactor.readSecret(secretKey = secretKey, passphrase = passphrase)

        verify { api.retrieve(RetrieveRequest(secretKey = secretKey, passphrase = passphrase)) }
        assertEquals(actual, expected)
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}