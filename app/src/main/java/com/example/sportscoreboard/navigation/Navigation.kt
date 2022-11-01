package com.example.sportscoreboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportscoreboard.domain.SportObject
import com.example.sportscoreboard.screens.entitiesList.SportObjectListScreen
import com.example.sportscoreboard.screens.sportObjectDetail.SportObjectDetailScreen
import com.squareup.moshi.JsonAdapter
import org.koin.androidx.compose.inject

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val sportObjectAdapter: JsonAdapter<SportObject> by inject()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.SportObjectsListScreen.route
    ){
        composable(
            route = NavigationScreens.SportObjectsListScreen.route,
        ){
            SportObjectListScreen(){
                sportObject ->
                    val json = sportObjectAdapter.toJson(sportObject)
                    navController.navigate(NavigationScreens.SportObjectDetailScreen.route + "/" + json)
            }
        }
        composable(
            route = NavigationScreens.SportObjectDetailScreen.route + "/{sportObject}",
            arguments = listOf(
                navArgument(name = "sportObject"){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val json:String = entry.arguments?.getString("sportObject") ?: ""
            val sportObject: SportObject? = sportObjectAdapter.fromJson(json)
            sportObject?.let { SportObjectDetailScreen(sportObject){
                navController.popBackStack()
            } }
        }
    }
}