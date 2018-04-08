package ru.alexandrkutashov.onetimesecret.presentation.metadata

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
import ru.alexandrkutashov.onetimesecret.data.repository.model.MetadataResponse
import ru.alexandrkutashov.onetimesecret.domain.MetadataInteractor
import ru.alexandrkutashov.onetimesecret.ext.OTPLink.secretLink
import ru.alexandrkutashov.onetimesecret.ext.Result

/**
 * Test for [MetadataPresenter]
 * 
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
class MetadataPresenterTest : KoinTest {

    companion object {
        private val SECRET_KEY = "SomeSecretKey"
        private val ERROR_MESSAGE = "Some error happened during sharing"
    }

    private val shareView by inject<MetadataView>()
    private val shareViewState by inject<`MetadataView$$State`>()
    private val interactor by inject<MetadataInteractor>()
    private val resourceManager by inject<Resources>()
    private lateinit var presenter: MetadataPresenter

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDataModule(), TestMetadataModule()))
        presenter = MetadataPresenter()
        presenter.attachView(shareView)
        presenter.setViewState(shareViewState)

        every { resourceManager.getString(any()) } answers { "" }
        every { resourceManager.getString(any(), any()) } answers { "" }
        every { resourceManager.getQuantityString(any(), any()) } answers { "" }
    }

    @Test
    fun shareSuccess() {

        val metadataKey = "someMetadataKey"
        val isPasswordRequired = true
        val expected = Result.Success(MetadataResponse(
                metadataKey = metadataKey,
                metadataTtl = 12345,
                secretKey = SECRET_KEY,
                isPassphraseRequired = isPasswordRequired
        ))

        coEvery { interactor.getSecretMetadata(allAny()) } answers { expected }
        presenter.getSecretMetadata(metadataKey)

        verifyOrder {
            shareViewState.showLoading(true)
            shareViewState.onMetadataSuccess(secretLink(SECRET_KEY), isPasswordRequired, any())
            shareViewState.showLoading(false)
        }
        verify(inverse = true) { shareViewState.onMetadataError(any()) }
    }

    @Test
    fun shareError() {

        val expected = Result.Error(Exception(ERROR_MESSAGE))

        coEvery { interactor.getSecretMetadata(allAny()) } answers { expected }
        presenter.getSecretMetadata("someSecret")

        verifyOrder {
            shareViewState.showLoading(true)
            shareViewState.onMetadataError(ERROR_MESSAGE)
            shareViewState.showLoading(false)
        }
        verify(inverse = true) { shareViewState.onMetadataSuccess(any(), any(), any()) }
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}