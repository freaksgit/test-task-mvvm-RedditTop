package com.vasylstoliarchuk.reddittops

import com.vasylstoliarchuk.reddittops.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class App: DaggerApplication() {

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder()
            .application(this)
            .build()
    }
}