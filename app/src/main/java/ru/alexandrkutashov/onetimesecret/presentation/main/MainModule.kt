package ru.alexandrkutashov.onetimesecret.presentation.main

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.MainInteractor

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class MainModule: Module {

    companion object {
        val MAIN = MainModule::javaClass.name
    }

    override fun invoke() = applicationContext {
        context(name = MAIN) {
            bean{ MainInteractor() }
        }
    }.invoke()
}