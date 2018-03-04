package ru.alexandrkutashov.onetimesecret.domain

import io.mockk.every
import io.mockk.verify
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.experimental.runBlocking
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
 * Test for [MainInteractor]
 *
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class MainInteractorTest : KoinTest {

    private val api by inject<OneTimeSecret>()

    private lateinit var interactor: MainInteractor

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDataModule()))
        interactor = MainInteractor()
    }

    @Test
    fun share() = runBlocking {
        val secret = "someSecret"
        val passphrase = "somePassphrase"
        val shareResponse = Result.Success(ShareResponse())

        every { api.share(any()) }.returns(shareResponse)

        val result = interactor.shareSecret(secret = secret, passphrase = passphrase)

        verify { api.share(ShareRequest(secret = secret, passphrase = passphrase)) }
        assertEquals(result, shareResponse)
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}