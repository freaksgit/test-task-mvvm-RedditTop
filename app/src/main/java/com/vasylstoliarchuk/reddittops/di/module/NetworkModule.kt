package com.vasylstoliarchuk.reddittops.di.module

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.vasylstoliarchuk.reddittops.BuildConfig
import com.vasylstoliarchuk.reddittops.data.remote.api.RedditApi
import com.vasylstoliarchuk.reddittops.data.remote.helper.ApiHelper
import com.vasylstoliarchuk.reddittops.data.remote.helper.ErrorMessageProvider
import com.vasylstoliarchuk.reddittops.data.remote.helper.ErrorMessageProviderImpl
import com.vasylstoliarchuk.reddittops.data.remote.helper.RetrofitApiHelper
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient {
        val httpClientBuilder = OkHttpClient.Builder()

        if (BuildConfig.DEBUG) {
            val interceptor = HttpLoggingInterceptor()
            interceptor.level = HttpLoggingInterceptor.Level.BODY
            httpClientBuilder.addInterceptor(interceptor)
        }

        return httpClientBuilder.build()
    }

    @Provides
    @Singleton
    fun provideGson(): Gson {
        val gsonBuilder = GsonBuilder()

        if (BuildConfig.DEBUG) {
            gsonBuilder.setPrettyPrinting()
        }

        return gsonBuilder.create()
    }

    @Provides
    fun provideGsonConverterFactory(gson: Gson): GsonConverterFactory {
        return GsonConverterFactory.create(gson)
    }

    @Provides
    fun provideRetrofit(client: OkHttpClient, gsonFactory: GsonConverterFactory): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://reddit.com")
            .client(client)
            .addConverterFactory(gsonFactory)
            .build()
    }


    @Provides
    @Singleton
    fun provideRedditApi(retrofit: Retrofit): RedditApi {
        return retrofit.create(RedditApi::class.java)
    }

    @Provides
    @Singleton
    fun provideApiHelper(gson: Gson, errorMessageProvider: ErrorMessageProvider): ApiHelper {
        return RetrofitApiHelper(gson, errorMessageProvider)
    }

    @Provides
    @Singleton
    fun provideErrorMessageProvider(@Named(APP_CONTEXT) context: Context): ErrorMessageProvider {
        return ErrorMessageProviderImpl(context.resources)
    }
}