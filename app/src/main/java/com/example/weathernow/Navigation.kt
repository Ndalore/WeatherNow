package com.example.weathernow

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument

@Composable
fun AppNavigation(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "login") {
        composable("login") { LoginScreen(navController) }
        composable("register") { RegisterScreen(navController) }

        composable("weather") {
            WeatherScreen(
                onNavigateToCityInfo = { city ->
                    navController.navigate("cityinfo/${city}")
                }
            )
        }

        composable(
            route = "cityinfo/{city}",
            arguments = listOf(navArgument("city") { type = NavType.StringType })
        ) { backStackEntry ->
            val city = backStackEntry.arguments?.getString("city") ?: ""
            CityinfoScreen(
                city = city,
                onBack = { navController.popBackStack() }
            )
        }
    }
}
