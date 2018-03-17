package ru.alexandrkutashov.onetimesecret.presentation

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareModule
import ru.terrakok.cicerone.Cicerone
import ru.terrakok.cicerone.NavigatorHolder
import ru.terrakok.cicerone.Router


/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class MainModule : Module {

    private val cicerone = Cicerone.create()

    companion object {
        val MAIN = ShareModule::javaClass.name
    }

    override fun invoke() = applicationContext {
        context(name = MAIN) {
            bean { cicerone.navigatorHolder as NavigatorHolder }
            bean { cicerone.router as Router }
        }
    }.invoke()
}