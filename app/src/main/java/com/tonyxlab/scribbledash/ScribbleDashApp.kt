package com.tonyxlab.scribbledash

import android.app.Application
import timber.log.Timber

class ScribbleDashApp : Application() {

    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}