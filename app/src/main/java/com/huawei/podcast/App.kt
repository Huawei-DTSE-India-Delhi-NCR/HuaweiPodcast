package com.huawei.podcast

import android.app.Application
import com.huawei.podcast.di.module.appModule
import com.huawei.podcast.di.module.repoModule
import com.huawei.podcast.di.module.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(listOf(appModule, repoModule, viewModelModule))
        }
    }
}
