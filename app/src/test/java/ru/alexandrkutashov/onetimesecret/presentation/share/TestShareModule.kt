package ru.alexandrkutashov.onetimesecret.presentation.share

import io.mockk.mockk
import io.mockk.spyk
import org.koin.dsl.module.Module
import org.koin.dsl.module.applicationContext
import ru.alexandrkutashov.onetimesecret.domain.ShareInteractor
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareModule.Companion.SHARE

/**
 * @author Alexandr Kutashov
 * on 04.03.2018
 */

class TestShareModule: Module {

    override fun invoke() = applicationContext {
        context(name = SHARE) {
            bean{ mockk<ShareInteractor>() }
            bean{ mockk<ShareView>() }
            bean{ spyk<`ShareView$$State`>() }
        }
    }.invoke()
}