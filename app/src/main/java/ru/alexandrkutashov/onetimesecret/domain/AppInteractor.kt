package ru.alexandrkutashov.onetimesecret.domain

import kotlinx.coroutines.experimental.CoroutineScope
import kotlinx.coroutines.experimental.Job
import kotlinx.coroutines.experimental.async
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
    private val jobs by inject<MutableList<Job>>()

    protected val api by inject<OneTimeSecret>()

    protected suspend fun <T> executeAsync(block: suspend CoroutineScope.() -> T): T =
            async(executors.networkContext, block = block).let { job ->
                jobs.add(job)
                job.invokeOnCompletion(onCancelling = true, invokeImmediately = false) {
                    jobs.remove(job)
                }
                job.await()
            }

    fun cancelJobs() {
        jobs.toTypedArray().forEach {
            it.cancel()
        }
    }
}

