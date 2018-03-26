package ru.alexandrkutashov.onetimesecret.domain

import junit.framework.Assert.assertEquals
import kotlinx.coroutines.experimental.*
import kotlinx.coroutines.experimental.sync.Mutex
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.koin.standalone.StandAloneContext.closeKoin
import org.koin.standalone.StandAloneContext.startKoin
import org.koin.standalone.inject
import org.koin.test.KoinTest
import ru.alexandrkutashov.onetimesecret.TestAppModule
import ru.alexandrkutashov.onetimesecret.ext.Executors

/**
 * Test for [AppInteractor]
 *
 * @author Alexandr Kutashov
 * on 25.03.2018
 */
class AppInteractorTest: KoinTest {

    private lateinit var interactor: TestInteractor
    private val jobs by inject<MutableList<Job>>()
    private val executors by inject<Executors>()

    @Before
    fun setUp() {
        startKoin(listOf(TestAppModule(), TestDomainModule()))
        interactor = TestInteractor()
    }

    @Test
    fun executeAsyncJobs() = runBlocking {
        val job1 = Mutex(true)
        launch(executors.uiContext) {
            interactor.execute {
                job1.lock()
            }
        }
        val job2 = Mutex(true)
        launch(executors.uiContext) {
            interactor.execute {
                job2.lock()
            }
        }
        assertEquals(2, jobs.size)

        job2.unlock()
        assertEquals(1, jobs.size)
    }

    @Test
    fun cancelJobs() = runBlocking {
        val job1 = Mutex(true)
        launch(executors.uiContext) {
            interactor.execute {
                job1.lock()
            }
        }
        val job2 = Mutex(true)
        launch(executors.uiContext) {
            interactor.execute {
                job2.lock()
            }
        }

        interactor.cancelJobs()
        assertEquals(0, jobs.size)
    }

    @After
    fun tearDown() {
        closeKoin()
    }
}

private class TestInteractor: AppInteractor() {

    suspend fun execute(block: suspend CoroutineScope.() -> Unit) {
        executeAsync(block)
    }
}