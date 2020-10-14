package com.vasylstoliarchuk.reddittops.ui.common.navigation

import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.AppActivity
import com.vasylstoliarchuk.reddittops.ui.toplist.TopPostsFragment

class AppNavigatorImpl(private val appActivity: AppActivity) : AppNavigator {
    private val fm = appActivity.supportFragmentManager

    override fun navigateToTopPosts(singleInstance: Boolean) {
        if (!singleInstance || fm.findFragmentById(R.id.fragmentContainer) == null) {
            fm.beginTransaction()
                .add(R.id.fragmentContainer, TopPostsFragment.newInstance())
                .commit()
        }
    }

    override fun navigateToImageDetails() {

    }
}