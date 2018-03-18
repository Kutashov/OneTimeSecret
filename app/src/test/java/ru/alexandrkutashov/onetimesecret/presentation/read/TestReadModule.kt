package ru.alexandrkutashov.onetimesecret.presentation.read

import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.ReadInteractor

/**
 * @author Alexandr Kutashov
 * on 18.03.2018
 */

class TestReadModule: Module {

    override fun invoke() = applicationContext {
        context(name = ReadModule.READ) {
            bean{ mockk<ReadInteractor>() }
            bean{ mockk<ReadView>() }
            bean{ spyk<`ReadView$$State`>() }
        }
    }.invoke()
}