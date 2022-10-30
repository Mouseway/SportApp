package com.example.sportscoreboard.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.sportscoreboard.domain.Entity
import com.example.sportscoreboard.screens.entitiesList.ParticipantsListScreen
import com.example.sportscoreboard.screens.entityDetail.EntityDetailScreen
import com.squareup.moshi.JsonAdapter
import org.koin.androidx.compose.inject

@Composable
fun Navigation(){
    val navController = rememberNavController()
    val entityAdapter: JsonAdapter<Entity> by inject()

    NavHost(
        navController = navController,
        startDestination = NavigationScreens.EntitiesListScreen.route
    ){
        composable(
            route = NavigationScreens.EntitiesListScreen.route,
        ){
            ParticipantsListScreen(){
                entity ->
                    val json = entityAdapter.toJson(entity)
                    navController.navigate(NavigationScreens.EntityDetailScreen.route + "/" + json)
            }
        }
        composable(
            route = NavigationScreens.EntityDetailScreen.route + "/{entity}",
            arguments = listOf(
                navArgument(name = "entity"){
                    type = NavType.StringType
                }
            )
        ) { entry ->
            val json:String = entry.arguments?.getString("entity") ?: ""
            val entity: Entity? = entityAdapter.fromJson(json)
            entity?.let { EntityDetailScreen(entity) }
        }
    }
}