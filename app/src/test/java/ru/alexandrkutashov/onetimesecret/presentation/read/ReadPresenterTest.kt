package ru.alexandrkutashov.onetimesecret.presentation.read

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
import ru.alexandrkutashov.onetimesecret.data.repository.model.RetrieveResponse
import ru.alexandrkutashov.onetimesecret.domain.ReadInteractor
import ru.alexandrkutashov.onetimesecret.ext.Result
import ru.alexandrkutashov.onetimesecret.presentation.LoadingFragment
import ru.alexandrkutashov.onetimesecret.presentation.TestMainModule
import ru.terrakok.cicerone.Router

/**
 * Test for [ReadPresenter]
 *
 * @author Alexandr Kutashov
 * on 18.03.2018
 */
class ReadPresenterTest : KoinTest {

    private val readView by inject<ReadView>()
    private val readViewState by inject<`ReadView$$State`>()
    private val interactor by inject<ReadInteractor>()
    private val router by inject<Router>()
    private lateinit var presenter: ReadPresenter

    companion object {
        private val SECRET_TEXT = "someSecretText"
        private val SECRET_KEY = "SomeSecretKey"
        private val ERROR_MESSAGE = "Some error happened during sharing"
    }

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDataModule(), TestMainModule(), TestReadModule()))
        presenter = ReadPresenter()
        presenter.attachView(readView)
        presenter.setViewState(readViewState)

        every { router.navigateTo(any()) } just Runs
        every { router.backTo(any()) } just Runs
        every { router.showSystemMessage(any()) } just Runs
    }

    @Test
    fun readSecretSuccess() {
        val expected = Result.Success(RetrieveResponse(
                value = SECRET_TEXT,
                secretKey = SECRET_KEY
        ))

        coEvery { interactor.readSecret(allAny()) } answers { expected }
        presenter.readSecret("someSecretLink")

        verifyOrder {
            router.navigateTo(LoadingFragment.screenKey)
            readViewState.onReadSuccess(SECRET_TEXT)
            router.backTo(ReadFragment.screenKey)
        }
        verify(inverse = true) { readViewState.onReadError(any()) }
    }

    @Test
    fun readSecretError() {
        val expected = Result.Error(Exception(ERROR_MESSAGE))

        coEvery { interactor.readSecret(allAny()) } answers { expected }
        presenter.readSecret("someSecretLink")

        verifyOrder {
            router.navigateTo(LoadingFragment.screenKey)
            readViewState.onReadError(ERROR_MESSAGE)
            router.backTo(ReadFragment.screenKey)
        }
        verify(inverse = true) { readViewState.onReadSuccess(any()) }
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}