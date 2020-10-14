package com.vasylstoliarchuk.reddittops.ui.common.navigation

interface AppNavigator {

    fun navigateToTopPosts(singleInstance:Boolean = true)

    fun navigateToImageDetails()
}