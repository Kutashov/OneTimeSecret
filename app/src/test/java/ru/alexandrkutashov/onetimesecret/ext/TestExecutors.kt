package ru.alexandrkutashov.onetimesecret.ext

import kotlinx.coroutines.experimental.Unconfined
import kotlin.coroutines.experimental.CoroutineContext

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestExecutors : Executors {
    override val networkContext: CoroutineContext
        get() = Unconfined

    override val uiContext: CoroutineContext
        get() = Unconfined
}