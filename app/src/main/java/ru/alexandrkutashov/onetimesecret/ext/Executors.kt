package ru.alexandrkutashov.onetimesecret.ext

import kotlinx.coroutines.experimental.CommonPool
import kotlinx.coroutines.experimental.android.UI
import kotlin.coroutines.experimental.CoroutineContext

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
        get() = CommonPool

    override val uiContext: CoroutineContext
        get() = UI
}