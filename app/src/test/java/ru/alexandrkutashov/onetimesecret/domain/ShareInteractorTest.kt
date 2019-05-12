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
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareRequest
import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareResponse
import ru.alexandrkutashov.onetimesecret.data.TestDataModule
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * Test for [ShareInteractor]
 *
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class ShareInteractorTest : KoinTest {

    private val api by inject<OneTimeSecret>()
    private lateinit var interactor: ShareInteractor

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDomainModule(), TestDataModule()))
        interactor = ShareInteractor()
    }

    @Test
    fun share() = runBlocking {
        val secret = "someSecret"
        val passphrase = "somePassphrase"
        val expected = Result.Success(ShareResponse(
                metadataKey = "someMetadataKey",
                metadataTtl = 12345
        ))

        every { api.share(any()) } answers { expected }

        val actual = interactor.shareSecret(secret = secret, passphrase = passphrase)

        verify { api.share(ShareRequest(secret = secret, passphrase = passphrase)) }
        assertEquals(actual, expected)
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}