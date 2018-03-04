package ru.alexandrkutashov.onetimesecret

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.ext.Executors
import ru.alexandrkutashov.onetimesecret.ext.ExecutorsImpl

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class AppModule : Module {

    override fun invoke() = applicationContext {
        bean { ExecutorsImpl() as Executors }
    }.invoke()
}