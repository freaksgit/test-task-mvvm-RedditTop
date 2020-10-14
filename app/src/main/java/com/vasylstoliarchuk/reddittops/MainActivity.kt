package com.vasylstoliarchuk.reddittops

import android.os.Bundle
import android.util.Log
import com.vasylstoliarchuk.reddittops.data.remote.RemoteDataSource
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import javax.inject.Inject


class MainActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var remoteDataSource: RemoteDataSource

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch {
            val topPosts = remoteDataSource.getTopPosts(limit = 10)
            Log.d("DEBUGLOG", topPosts.toString())
        }
    }
}