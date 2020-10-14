package com.vasylstoliarchuk.reddittops.di.module

import com.google.gson.Gson
import com.vasylstoliarchuk.reddittops.data.Repository
import com.vasylstoliarchuk.reddittops.data.RepositoryImpl
import com.vasylstoliarchuk.reddittops.data.remote.RemoteDataSource
import com.vasylstoliarchuk.reddittops.data.remote.RetrofitDataSource
import com.vasylstoliarchuk.reddittops.data.remote.api.RedditApi
import com.vasylstoliarchuk.reddittops.data.remote.helper.ApiHelper
import com.vasylstoliarchuk.reddittops.data.remote.helper.RetrofitApiHelper
import dagger.Module
import dagger.Provides
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
    @Singleton
    fun provideApiHelper(gson: Gson): ApiHelper {
        return RetrofitApiHelper(gson)
    }
}