package com.tonyxlab.scribbledash.navigation

import androidx.annotation.DrawableRes
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.tonyxlab.scribbledash.R
import com.tonyxlab.scribbledash.navigation.Destinations.DifficultyLevelDestination
import com.tonyxlab.scribbledash.navigation.Destinations.DrawScreenDestination
import com.tonyxlab.scribbledash.navigation.Destinations.HomeScreenDestination
import com.tonyxlab.scribbledash.presentation.core.components.EmptyScreen
import com.tonyxlab.scribbledash.presentation.screens.difficulty.DifficultyLevelScreen
import com.tonyxlab.scribbledash.presentation.screens.difficulty.DifficultyLevelScreenContent
import com.tonyxlab.scribbledash.presentation.screens.draw.DrawScreen
import com.tonyxlab.scribbledash.presentation.screens.home.HomeScreen
import com.tonyxlab.scribbledash.presentation.screens.home.components.GameMode
import kotlinx.serialization.Serializable


fun NavGraphBuilder.appDestinations(
    navController: NavController,
    modifier: Modifier = Modifier
) {
    composable<Destinations.ChartDestination> {

        EmptyScreen(screenText = stringResource(R.string.text_challenge_screen))
    }
    composable<HomeScreenDestination> {

        HomeScreen(
                modifier = modifier,
                navController = navController,
                gameMode = GameMode.OneRoundWonder,
                onSelectGameMode = {
                    navController.navigate(
                            DifficultyLevelDestination
                    )
                }
        )
    }

    composable<DifficultyLevelDestination> {

        DifficultyLevelScreen(
                modifier = modifier,
                onClose = { navController.popBackStack() },
                onSelectDifficultyLevel = { navController.navigate(DrawScreenDestination)})
    }

    composable<DrawScreenDestination> {

        DrawScreen(modifier = modifier, onClose = { navController.popBackStack() })
    }
}

sealed class Destinations {

    @Serializable
    data object HomeScreenDestination : Destinations()

    @Serializable
    data object DifficultyLevelDestination : Destinations()

    @Serializable
    data object DrawScreenDestination : Destinations()

    @Serializable
    data object ChartDestination : Destinations()

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
            route = Destinations.ChartDestination
    ),

    HOME(
            label = "Home",
            icon = R.drawable.ic_home,
            route = HomeScreenDestination
    ),
}
