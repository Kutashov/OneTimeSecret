package ru.alexandrkutashov.onetimesecret.presentation

import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.MainInteractor
import ru.alexandrkutashov.onetimesecret.presentation.main.MainModule.Companion.MAIN
import ru.alexandrkutashov.onetimesecret.presentation.main.MainView
import ru.alexandrkutashov.onetimesecret.presentation.main.`MainView$$State`

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestMainModule: Module {

    override fun invoke() = applicationContext {
        context(name = MAIN) {
            bean{ mockk<MainInteractor>() }
            bean{ mockk<MainView>() }
            bean{ spyk<`MainView$$State`>() }
        }
    }.invoke()
}