package ru.alexandrkutashov.onetimesecret.presentation

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
import ru.alexandrkutashov.onetimesecret.domain.MainInteractor
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.presentation.main.MainPresenter
import ru.alexandrkutashov.onetimesecret.presentation.main.MainView
import ru.alexandrkutashov.onetimesecret.presentation.main.`MainView$$State`

/**
 * Test for [MainPresenter]
 *
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class MainPresenterTest : KoinTest {

    private val homeView by inject<MainView>()
    private val homeViewState by inject<`MainView$$State`>()
    private val interactor by inject<MainInteractor>()
    private lateinit var presenter: MainPresenter

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDataModule(), TestMainModule()))
        presenter = MainPresenter()
        presenter.attachView(homeView)
        presenter.setViewState(homeViewState)
    }

    @Test
    fun shareEmptySecret() {
        presenter.shareSecret("")

        verify { homeViewState.onEmptySecret() }
        verify { interactor wasNot Called }
    }

    @Test
    fun shareSuccess() {

        val expected = Result.Success(ShareResponse(
                metadataKey = "someMetadataKey",
                metadataTtl = 12345
        ))

        coEvery { interactor.shareSecret(allAny()) } answers { expected }
        presenter.shareSecret("someSecret")

        verifyOrder {
            homeViewState.showLoading(true)
            homeViewState.onShareSuccess()
            homeViewState.showLoading(false)
        }
        verify(inverse = true) { homeViewState.onShareError(any()) }
    }

    @Test
    fun shareError() {
        val errorMessage = "Some error happened during sharing"
        val expected = Result.Error(Exception(errorMessage))

        coEvery { interactor.shareSecret(allAny()) } answers { expected }
        presenter.shareSecret("someSecret")

        verifyOrder {
            homeViewState.showLoading(true)
            homeViewState.onShareError(errorMessage)
            homeViewState.showLoading(false)
        }
        verify(inverse = true) { homeViewState.onShareSuccess() }
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}