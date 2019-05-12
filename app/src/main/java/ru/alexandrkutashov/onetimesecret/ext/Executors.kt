package ru.alexandrkutashov.onetimesecret.ext

import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

interface Executors {
    val networkContext: CoroutineContext
    val uiContext: CoroutineContext
}

class ExecutorsImpl : Executors {
    override val networkContext: CoroutineContext
        get() = Dispatchers.IO

    override val uiContext: CoroutineContext
        get() = Dispatchers.Main
}