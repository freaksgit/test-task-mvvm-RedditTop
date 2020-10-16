package com.vasylstoliarchuk.reddittops.di.module

import android.content.Context
import com.vasylstoliarchuk.reddittops.data.Repository
import com.vasylstoliarchuk.reddittops.data.RepositoryImpl
import com.vasylstoliarchuk.reddittops.data.remote.RemoteDataSource
import com.vasylstoliarchuk.reddittops.data.remote.RetrofitDataSource
import com.vasylstoliarchuk.reddittops.data.remote.api.RedditApi
import com.vasylstoliarchuk.reddittops.data.remote.helper.ApiHelper
import com.vasylstoliarchuk.reddittops.ui.image.ImageHelper
import com.vasylstoliarchuk.reddittops.ui.image.ImageHelperImpl
import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton

@Module
class DataModule {

    @Provides
    @Singleton
    fun provideRepository(remoteDataSource: RemoteDataSource): Repository {
        return RepositoryImpl(remoteDataSource)
    }

    @Provides
    @Singleton
    fun provideRemoteDataSource(redditApi: RedditApi, apiHelper: ApiHelper): RemoteDataSource {
        return RetrofitDataSource(redditApi, apiHelper)
    }

    @Provides
    fun provideImageHelper(@Named(APP_CONTEXT) context: Context): ImageHelper {
        return ImageHelperImpl(context)
    }
}