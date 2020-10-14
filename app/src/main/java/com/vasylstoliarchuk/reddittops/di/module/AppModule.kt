package com.vasylstoliarchuk.reddittops.di.module

import android.content.Context
import com.vasylstoliarchuk.reddittops.App
import com.vasylstoliarchuk.reddittops.di.scope.ActivityScope
import com.vasylstoliarchuk.reddittops.ui.AppActivity
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector
import javax.inject.Named
import javax.inject.Singleton

@Module
abstract class AppModule {

    @Binds
    @Singleton
    @Named(APP_CONTEXT)
    abstract fun provideApplicationContext(app: App): Context

    @ActivityScope
    @ContributesAndroidInjector(modules = [AppActivityModule::class])
    internal abstract fun appActivityInjector(): AppActivity

}