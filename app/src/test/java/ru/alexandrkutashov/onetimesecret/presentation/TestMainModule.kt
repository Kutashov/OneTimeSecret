package ru.alexandrkutashov.onetimesecret.presentation

import io.mockk.mockk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareModule
import ru.terrakok.cicerone.Router

/**
 * @author Alexandr Kutashov
 * on 17.03.2018
 */

class TestMainModule: Module {

    override fun invoke() = applicationContext {
        context(name = ShareModule.SHARE) {
            bean{ mockk<Router>() }
        }
    }.invoke()
}