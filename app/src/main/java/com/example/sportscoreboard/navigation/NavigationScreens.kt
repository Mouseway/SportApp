package com.example.sportscoreboard.navigation

sealed class NavigationScreens(val route: String) {
    object EntitiesListScreen : NavigationScreens("listScreen")
    object EntityDetailScreen : NavigationScreens("detailScreen")
}
