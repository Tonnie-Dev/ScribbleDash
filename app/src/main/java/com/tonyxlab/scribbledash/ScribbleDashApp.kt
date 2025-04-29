package com.tonyxlab.scribbledash

import android.app.Application
import com.tonyxlab.scribbledash.di.appModule


import com.tonyxlab.scribbledash.di.randomVectorProviderModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import org.koin.core.logger.Level
import timber.log.Timber

class ScribbleDashApp : Application() {

    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger(Level.DEBUG) // Use Level.INFO or Level.NONE in release
            androidContext(this@ScribbleDashApp) // Provide the application context
            modules(listOf(appModule,  randomVectorProviderModule))

        }
    }
}