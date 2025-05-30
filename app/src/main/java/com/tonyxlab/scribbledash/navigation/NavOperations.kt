package com.tonyxlab.scribbledash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController

class NavOperations(val navController: NavHostController) {

    fun navigateToDestination(destination: Destinations) {

        navController.navigate(destination)
    }

    fun navigateToHomeDestination() {

        navController.navigate(Destinations.HomeScreenDestination)
    }

    fun navigateToDifficultyScreen(gameMode: Int = -1) {

        navController.navigate(Destinations.DifficultyDestination(gameMode = gameMode))

    }

    fun navigateToDrawScreen(gameMode: Int = -1, gameLevel: Int) {

        navController.navigate(
                Destinations.DrawScreenDestination(
                        gameMode = gameMode,
                        gameLevel = gameLevel
                )
        )

    }

    fun popBackStack() {

        navController.popBackStack()
    }
}


@Composable
fun rememberNavOperations(
    navController: NavHostController = rememberNavController()
): NavOperations {
    return remember { NavOperations(navController) }
}