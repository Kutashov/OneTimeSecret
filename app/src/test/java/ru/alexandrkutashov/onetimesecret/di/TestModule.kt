package ru.alexandrkutashov.onetimesecret.di

import io.mockk.mockk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.ext.Executors

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestModule : Module {

    private val api = mockk<OneTimeSecret>()
    private val executors = TestExecutors()

    override fun invoke() = applicationContext {
        bean { api }
        bean { executors as Executors }
    }.invoke()
}