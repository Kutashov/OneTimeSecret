package ru.alexandrkutashov.onetimesecret.domain

import kotlinx.coroutines.Job
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * @author Alexandr Kutashov
 * on 25.03.2018
 */
class DomainModule : Module {

    override fun invoke() = applicationContext {
        factory { Job() }
    }.invoke()
}