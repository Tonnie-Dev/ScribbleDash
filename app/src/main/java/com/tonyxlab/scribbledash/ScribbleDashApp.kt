package com.tonyxlab.scribbledash

import android.app.Application
import com.tonyxlab.scribbledash.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import timber.log.Timber

class ScribbleDashApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidContext(this@ScribbleDashApp)
            modules(appModule)

        }
    }
}