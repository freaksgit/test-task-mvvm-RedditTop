package com.vasylstoliarchuk.reddittops.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.vasylstoliarchuk.reddittops.di.ViewModelFactory
import com.vasylstoliarchuk.reddittops.di.ViewModelKey
import com.vasylstoliarchuk.reddittops.ui.toplist.TopPostsViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import javax.inject.Singleton

@Module
abstract class ViewModelModule {

    @Binds
    @Singleton
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(TopPostsViewModel::class)
    internal abstract fun topPostsViewModel(viewModel: TopPostsViewModel): ViewModel

    //Add more ViewModels here
}