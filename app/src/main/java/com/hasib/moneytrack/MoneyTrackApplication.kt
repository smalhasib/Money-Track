package com.hasib.moneytrack

import android.app.Application
import timber.log.Timber

class MoneyTrackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
    }
}