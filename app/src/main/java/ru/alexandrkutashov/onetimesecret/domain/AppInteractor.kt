package ru.alexandrkutashov.onetimesecret.domain

import kotlinx.coroutines.*
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.ext.Executors

/**
 * @author Alexandr Kutashov
 * on 25.03.2018
 */
open class AppInteractor : KoinComponent {

    private val executors by inject<Executors>()
    private val job by inject<Job>()

    protected val api by inject<OneTimeSecret>()

    protected suspend fun <T> executeAsync(block: suspend CoroutineScope.() -> T): T =
        CoroutineScope(job).async(executors.networkContext, block = block).let { job ->
            job.await()
        }

    fun cancelJobs() {
        job.children.count()
        job.cancel()
    }
}

