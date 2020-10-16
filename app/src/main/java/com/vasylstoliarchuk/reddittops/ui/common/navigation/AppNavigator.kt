package com.vasylstoliarchuk.reddittops.ui.common.navigation

import android.view.View

interface AppNavigator {

    fun navigateToTopPosts(singleInstance:Boolean = true)

    fun navigateToImageDetails(sharedElementView: View, id: String, imageUrl: String?)
}