package com.vasylstoliarchuk.reddittops.ui.common.navigation

import android.view.View
import com.vasylstoliarchuk.reddittops.R
import com.vasylstoliarchuk.reddittops.ui.AppActivity
import com.vasylstoliarchuk.reddittops.ui.image.ImageFragment
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

    override fun navigateToImageDetails(sharedElementView: View, id: String, imageUrl: String?) {
        fm.beginTransaction()
            .addSharedElement(sharedElementView, sharedElementView.transitionName)
            .addToBackStack(null)
            .replace(R.id.fragmentContainer, ImageFragment.newInstance(id, imageUrl, sharedElementView.transitionName))
            .commit();
    }
}