package com.example.sportscoreboard

import android.app.Application
import com.example.sportscoreboard.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            // declare used Android context
            androidContext(this@App)
            // declare modules
            modules(appModule)
        }
    }
}