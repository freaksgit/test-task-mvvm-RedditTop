package com.vasylstoliarchuk.reddittops.ui

import android.os.Bundle
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.common.navigation.AppNavigator
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_app.*
import javax.inject.Inject


class AppActivity : DaggerAppCompatActivity() {

    @Inject
    lateinit var navigator: AppNavigator

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_app)
        setSupportActionBar(toolbar)
        navigator.navigateToTopPosts(true)
    }
}