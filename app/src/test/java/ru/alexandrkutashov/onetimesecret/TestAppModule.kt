package ru.alexandrkutashov.onetimesecret

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.ext.TestExecutors
import ru.alexandrkutashov.onetimesecret.ext.Executors

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestAppModule : Module {

    override fun invoke() = applicationContext {
        bean { TestExecutors() as Executors }
    }.invoke()
}