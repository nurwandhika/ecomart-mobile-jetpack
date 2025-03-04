package com.dicoding.jetreward.ui.navigation

sealed class Screen(val route: String) {
    object Home : Screen("home")
    object Cart : Screen("cart")
    object Profile : Screen("profile")
    object DetailReward : Screen("home/{productId}") {
        fun createRoute(productId: Long) = "home/$productId"
    }
}