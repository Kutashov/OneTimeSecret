package ru.alexandrkutashov.onetimesecret.presentation.share

import android.content.res.Resources
import io.mockk.*
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import ru.alexandrkutashov.onetimesecret.TestAppModule
import ru.alexandrkutashov.onetimesecret.data.TestDataModule
import ru.alexandrkutashov.onetimesecret.data.repository.model.ShareResponse
import ru.alexandrkutashov.onetimesecret.domain.ShareInteractor
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.secretLink
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * Test for [SharePresenter]
 *
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class SharePresenterTest : KoinTest {

    companion object {
        private val EMPTY_SECRET = ""
        private val SECRET_KEY = "SomeSecretKey"
        private val ERROR_MESSAGE = "Some error happened during sharing"
    }

    private val shareView by inject<ShareView>()
    private val shareViewState by inject<`ShareView$$State`>()
    private val interactor by inject<ShareInteractor>()
    private val resourceManager by inject<Resources>()
    private lateinit var presenter: SharePresenter

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDataModule(), TestShareModule()))
        presenter = SharePresenter()
        presenter.attachView(shareView)
        presenter.setViewState(shareViewState)

        every { resourceManager.getString(any()) } answers { "" }
    }

    @Test
    fun shareEmptySecret() {
        presenter.shareSecret(EMPTY_SECRET)

        verify { shareViewState.onShareError(any()) }
        verify { interactor wasNot Called }
    }

    @Test
    fun shareSuccess() {

        val metadataKey = "someMetadataKey"
        val expected = Result.Success(ShareResponse(
                metadataKey = metadataKey,
                metadataTtl = 12345,
                secretKey = SECRET_KEY
        ))

        coEvery { interactor.shareSecret(allAny()) } answers { expected }
        presenter.shareSecret("someSecret")

        verifyOrder {
            shareViewState.showLoading(true)
            shareViewState.onShareSuccess(secretLink(SECRET_KEY), metadataKey)
            shareViewState.showLoading(false)
        }
        verify(inverse = true) { shareViewState.onShareError(any()) }
    }

    @Test
    fun shareError() {

        val expected = Result.Error(Exception(ERROR_MESSAGE))

        coEvery { interactor.shareSecret(allAny()) } answers { expected }
        presenter.shareSecret("someSecret")

        verifyOrder {
            shareViewState.showLoading(true)
            shareViewState.onShareError(ERROR_MESSAGE)
            shareViewState.showLoading(false)
        }
        verify(inverse = true) { shareViewState.onShareSuccess(any(), any()) }
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}