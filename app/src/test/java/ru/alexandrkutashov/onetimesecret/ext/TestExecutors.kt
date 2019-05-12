package ru.alexandrkutashov.onetimesecret.ext

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestExecutors : Executors {
    override val networkContext: CoroutineContext
        get() = Dispatchers.Unconfined

    override val uiContext: CoroutineContext
        get() = Dispatchers.Unconfined
}