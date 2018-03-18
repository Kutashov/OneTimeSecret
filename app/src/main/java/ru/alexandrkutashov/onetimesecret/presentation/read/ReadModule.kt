package ru.alexandrkutashov.onetimesecret.presentation.read

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.ReadInteractor

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class ReadModule: Module {

    companion object {
        val READ = ReadModule::javaClass.name
    }

    override fun invoke() = applicationContext {
        context(name = READ) {
            bean{ ReadInteractor() }
        }
    }.invoke()
}