package ru.alexandrkutashov.onetimesecret.presentation.metadata

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.MetadataInteractor

/**
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
class MetadataModule: Module {

    companion object {
        val METADATA = MetadataModule::javaClass.name
    }

    override fun invoke() = applicationContext {
        context(name = METADATA) {
            bean{ MetadataInteractor() }
        }
    }.invoke()
}