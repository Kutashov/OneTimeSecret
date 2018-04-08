package ru.alexandrkutashov.onetimesecret.presentation.metadata

import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.MetadataInteractor

/**
 * @author Alexandr Kutashov
 * on 08.04.2018
 */
class TestMetadataModule: Module {

    override fun invoke() = applicationContext {
        context(name = MetadataModule.METADATA) {
            bean{ mockk<MetadataInteractor>() }
            bean{ mockk<MetadataView>() }
            bean{ spyk<`MetadataView$$State`>() }
        }
    }.invoke()
}