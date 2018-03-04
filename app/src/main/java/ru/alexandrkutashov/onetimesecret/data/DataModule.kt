package ru.alexandrkutashov.onetimesecret.data

import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecret
import ru.alexandrkutashov.onetimesecret.data.repository.OneTimeSecretImpl

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */

class DataModule : Module {

    override fun invoke() = applicationContext {
        bean { OneTimeSecretImpl() as OneTimeSecret }
    }.invoke()
}