package ru.alexandrkutashov.onetimesecret.domain

import kotlinx.coroutines.experimental.Job
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext

/**
 * @author Alexandr Kutashov
 * on 25.03.2018
 */
class TestDomainModule : Module {

    override fun invoke() = applicationContext {
        bean { mutableListOf<Job>() }
    }.invoke()
}