package com.example.sportscoreboard.navigation

sealed class NavigationScreens(val route: String) {
    object SportObjectsListScreen : NavigationScreens("listScreen")
    object SportObjectDetailScreen : NavigationScreens("detailScreen")
}
