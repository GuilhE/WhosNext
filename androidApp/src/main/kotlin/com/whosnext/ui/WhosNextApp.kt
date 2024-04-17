package com.whosnext.ui

import android.app.Application
import android.content.Context
import com.whosnext.app.DependencyInjection
import com.whosnext.app.sharedPrefsForPlatformDependencies
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger

class WhosNextApp : Application() {

    override fun onCreate() {
        super.onCreate()
        sharedPrefsForPlatformDependencies = applicationContext.getSharedPreferences(applicationContext.packageName, Context.MODE_PRIVATE)
        DependencyInjection.initKoin {
            androidLogger()
            androidContext(this@WhosNextApp)
        }
    }
}