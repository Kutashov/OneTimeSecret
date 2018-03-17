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
import ru.alexandrkutashov.onetimesecret.presentation.LoadingFragment
import ru.alexandrkutashov.onetimesecret.presentation.TestMainModule
import ru.terrakok.cicerone.Router

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

    private val homeView by inject<ShareView>()
    private val homeViewState by inject<`ShareView$$State`>()
    private val interactor by inject<ShareInteractor>()
    private val router by inject<Router>()
    private val resourceManager by inject<Resources>()
    private lateinit var presenter: SharePresenter

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDataModule(), TestMainModule(), TestShareModule()))
        presenter = SharePresenter()
        presenter.attachView(homeView)
        presenter.setViewState(homeViewState)

        every { resourceManager.getString(any()) } answers { "" }
        every { router.navigateTo(any()) } just Runs
        every { router.backTo(any()) } just Runs
        every { router.showSystemMessage(any()) } just Runs
    }

    @Test
    fun shareEmptySecret() {
        presenter.shareSecret(EMPTY_SECRET)

        verify { router.showSystemMessage(any()) }
        verify { interactor wasNot Called }
    }

    @Test
    fun shareSuccess() {

        val expected = Result.Success(ShareResponse(
                metadataKey = "someMetadataKey",
                metadataTtl = 12345,
                secretKey = SECRET_KEY
        ))

        coEvery { interactor.shareSecret(allAny()) } answers { expected }
        presenter.shareSecret("someSecret")

        verifyOrder {
            router.navigateTo(LoadingFragment.screenKey)
            homeViewState.onShareSuccess(secretLink(SECRET_KEY))
        }
        verify(inverse = true) { homeViewState.onShareError(any()) }
    }

    @Test
    fun shareError() {

        val expected = Result.Error(Exception(ERROR_MESSAGE))

        coEvery { interactor.shareSecret(allAny()) } answers { expected }
        presenter.shareSecret("someSecret")

        verifyOrder {
            router.navigateTo(LoadingFragment.screenKey)
            homeViewState.onShareError(ERROR_MESSAGE)
            router.backTo(ShareFragment.screenKey)
        }
        verify(inverse = true) { homeViewState.onShareSuccess(any()) }
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}