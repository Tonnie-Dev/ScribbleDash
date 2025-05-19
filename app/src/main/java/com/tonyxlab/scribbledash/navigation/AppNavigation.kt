package com.tonyxlab.scribbledash.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.navigation.Destinations.DifficultyDestination
import com.tonyxlab.scribbledash.navigation.Destinations.StatsDestination
import com.tonyxlab.scribbledash.navigation.Destinations.DrawScreenDestination
import com.tonyxlab.scribbledash.navigation.Destinations.HomeScreenDestination
import com.tonyxlab.scribbledash.navigation.Destinations.PreviewDestination
import com.tonyxlab.scribbledash.presentation.screens.difficulty.DifficultyLevelScreen
import com.tonyxlab.scribbledash.presentation.screens.draw.DrawScreen
import com.tonyxlab.scribbledash.presentation.screens.home.HomeScreen
import com.tonyxlab.scribbledash.presentation.screens.preview.PreviewScreen
import com.tonyxlab.scribbledash.presentation.screens.stats.StatsScreen
import kotlinx.serialization.Serializable


fun NavGraphBuilder.appDestinations(
    navOperations: NavOperations,
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable<StatsDestination> {

        StatsScreen(
                modifier = modifier,
                navOperations = navOperations,
                navController = navController
        )
    }
    composable<HomeScreenDestination> {

        HomeScreen(
                modifier = modifier,
                navController = navController,
                navOperations = navOperations
        )
    }

    composable< DifficultyDestination> {

        DifficultyLevelScreen(
                modifier = modifier,
                navOperations = navOperations,

        )
    }

    composable<DrawScreenDestination> {

        DrawScreen(
                modifier = modifier,
                onClose = { navController.popBackStack() },
                onSubmit = { sampleSvgStrings, userPathStrings, viewPortWidth, viewPortHeight, similarityScore ->


                    navController.navigate(
                            PreviewDestination(
                                    sampleSvgStrings = sampleSvgStrings,
                                    userPathStrings = userPathStrings,
                                    viewPortWidth = viewPortWidth,
                                    viewPortHeight = viewPortHeight,
                                    similarityScore = similarityScore
                            )
                    )
                }

        )
    }

    composable<PreviewDestination> { backStack ->

        PreviewScreen(modifier = modifier, navOperations = navOperations)
    }
}

sealed class Destinations {

    @Serializable
    data object StatsDestination : Destinations()

    @Serializable
    data object HomeScreenDestination : Destinations()

    @Serializable
    data class DifficultyDestination(val gameMode: Int = -1) : Destinations()

    @Serializable
    data  class DrawScreenDestination(val gameMode:Int = -1, val gameLevel: Int = -1) : Destinations()

    @Serializable
    data class PreviewDestination(
        val sampleSvgStrings: List<String>,
        val userPathStrings: List<String>,
        val viewPortWidth: Float,
        val viewPortHeight: Float,
        val similarityScore: Int
    ) : Destinations()

}

enum class BottomNavigationOptions(
    val label: String,
    @DrawableRes
    val icon: Int,
    val route: Destinations
) {

    CHART(
            label = "Chart",
            icon = R.drawable.ic_chart,
            route = StatsDestination
    ),

    HOME(
            label = "Home",
            icon = R.drawable.ic_home,
            route = HomeScreenDestination
    ),
}


