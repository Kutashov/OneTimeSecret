package ru.alexandrkutashov.onetimesecret.di

import kotlinx.coroutines.experimental.Unconfined
import ru.alexandrkutashov.onetimesecret.ext.Executors
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