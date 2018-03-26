package ru.alexandrkutashov.onetimesecret

import android.app.Application
import android.os.StrictMode
import org.koin.Koin
import org.koin.android.ext.android.startKoin
import ru.alexandrkutashov.onetimesecret.data.DataModule
import ru.alexandrkutashov.onetimesecret.domain.DomainModule
import ru.alexandrkutashov.onetimesecret.presentation.MainModule
import ru.alexandrkutashov.onetimesecret.presentation.read.ReadModule
import ru.alexandrkutashov.onetimesecret.presentation.share.ShareModule

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class OTSApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Koin.useContextIsolation = true
        startKoin(this, listOf(
                AppModule(this),
                DataModule(),
                DomainModule(),
                MainModule(),
                ShareModule(),
                ReadModule()
        ))

        if (BuildConfig.DEBUG) {
            val policy = StrictMode.VmPolicy.Builder()
                    .detectAll()
                    .penaltyLog()
                    .build()
            StrictMode.setVmPolicy(policy)
        }
    }
}