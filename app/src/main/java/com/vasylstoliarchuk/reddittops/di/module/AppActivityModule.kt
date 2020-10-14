package com.vasylstoliarchuk.reddittops.di.module

import com.vasylstoliarchuk.reddittops.di.scope.ActivityScope
import com.vasylstoliarchuk.reddittops.di.scope.FragmentScope
import com.vasylstoliarchuk.reddittops.ui.AppActivity
import com.vasylstoliarchuk.reddittops.ui.common.navigation.AppNavigator
import com.vasylstoliarchuk.reddittops.ui.common.navigation.AppNavigatorImpl
import com.vasylstoliarchuk.reddittops.ui.toplist.TopPostsFragment
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector

@Module
abstract class AppActivityModule {

    @FragmentScope
    @ContributesAndroidInjector
    internal abstract fun topPostsFragmentInjector(): TopPostsFragment

    companion object {
        @Provides
        @ActivityScope
        fun provideAppNavigator(appActivity: AppActivity): AppNavigator {
            return AppNavigatorImpl(appActivity)
        }
    }
}