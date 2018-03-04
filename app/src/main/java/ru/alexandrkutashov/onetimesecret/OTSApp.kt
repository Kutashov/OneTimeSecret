package ru.alexandrkutashov.onetimesecret

import android.app.Application
import org.koin.Koin
import org.koin.android.ext.android.startKoin
import ru.alexandrkutashov.onetimesecret.data.DataModule
import ru.alexandrkutashov.onetimesecret.presentation.main.MainModule

/**
 * @author Alexandr Kutashov
 * on 25.02.2018
 */
class OTSApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Koin.useContextIsolation = true
        startKoin(this, listOf(
                AppModule(),
                DataModule(),
                MainModule()
        ))
    }
}