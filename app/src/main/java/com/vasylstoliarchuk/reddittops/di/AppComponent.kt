package com.vasylstoliarchuk.reddittops.di

import com.vasylstoliarchuk.reddittops.App
import com.vasylstoliarchuk.reddittops.di.module.AppModule
import com.vasylstoliarchuk.reddittops.di.module.DataModule
import com.vasylstoliarchuk.reddittops.di.module.NetworkModule
import com.vasylstoliarchuk.reddittops.di.module.ViewModelModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AppModule::class,
        AndroidSupportInjectionModule::class,
        DataModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)
interface AppComponent : AndroidInjector<App> {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: App): Builder

        fun build(): AppComponent
    }

    override fun inject(app: App)
}