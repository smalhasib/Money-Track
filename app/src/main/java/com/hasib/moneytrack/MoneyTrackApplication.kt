package com.hasib.moneytrack

import android.app.Application
import com.hasib.moneytrack.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin
import timber.log.Timber

class MoneyTrackApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(Timber.DebugTree())
        startKoin {
            androidContext(this@MoneyTrackApplication)
            androidLogger()

            modules(appModule)
        }
    }
}