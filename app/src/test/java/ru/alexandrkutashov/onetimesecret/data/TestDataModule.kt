package ru.alexandrkutashov.onetimesecret.data

import io.mockk.mockk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestDataModule : Module {

    override fun invoke() = applicationContext {
        bean { mockk<OneTimeSecret>() }
    }.invoke()
}