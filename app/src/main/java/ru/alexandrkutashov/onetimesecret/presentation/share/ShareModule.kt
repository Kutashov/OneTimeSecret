package ru.alexandrkutashov.onetimesecret.presentation.share

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.ShareInteractor

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class ShareModule: Module {

    companion object {
        val SHARE = ShareModule::javaClass.name
    }

    override fun invoke() = applicationContext {
        context(name = SHARE) {
            bean{ ShareInteractor() }
        }
    }.invoke()
}