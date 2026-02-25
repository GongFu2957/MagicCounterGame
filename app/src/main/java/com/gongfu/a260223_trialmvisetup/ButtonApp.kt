package com.gongfu.a260223_trialmvisetup

import android.app.Application
import com.gongfu.a260223_trialmvisetup.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class ButtonApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@ButtonApp)
            androidLogger()

            modules(appModule)
        }
    }
}