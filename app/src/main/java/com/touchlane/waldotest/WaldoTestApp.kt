package com.touchlane.waldotest

import androidx.multidex.MultiDexApplication
import com.touchlane.waldotest.di.domainModule
import com.touchlane.waldotest.di.networkModule
import com.touchlane.waldotest.di.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WaldoTestApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WaldoTestApp)
            modules(
                networkModule,
                domainModule,
                viewModelModule
            )
        }
    }
}