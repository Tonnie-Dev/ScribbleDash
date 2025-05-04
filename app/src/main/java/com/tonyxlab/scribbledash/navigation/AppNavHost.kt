package com.tonyxlab.scribbledash.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost

@Composable
fun AppNavHost(
    navOperations: NavOperations,
    modifier: Modifier = Modifier
) {
    NavHost(
            navController = navOperations.navController,
            startDestination = Destinations.HomeScreenDestination
    ) {
        appDestinations(
                navController = navOperations.navController,
                navOperations = navOperations,
                modifier = modifier
        )
    }
}