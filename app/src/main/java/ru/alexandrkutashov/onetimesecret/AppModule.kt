package ru.alexandrkutashov.onetimesecret

import android.content.Context
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.ext.Executors
import ru.alexandrkutashov.onetimesecret.ext.ExecutorsImpl

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class AppModule(private val context: Context) : Module {

    override fun invoke() = applicationContext {
        bean { context.resources }
        bean { ExecutorsImpl() as Executors }
    }.invoke()
}