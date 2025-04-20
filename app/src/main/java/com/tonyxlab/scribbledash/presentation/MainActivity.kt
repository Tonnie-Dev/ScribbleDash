package com.tonyxlab.scribbledash.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.SystemBarStyle
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.toArgb
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tonyxlab.scribbledash.navigation.Destinations.HomeScreenDestination
import com.tonyxlab.scribbledash.navigation.appDestinations
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.Background
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge(
                navigationBarStyle = SystemBarStyle.light(
                        scrim = Background.toArgb(),
                        darkScrim = Background.toArgb()
                )
        )

        setContent {
            ScribbleDashTheme {
                val navController = rememberNavController()
                val padding = MaterialTheme.spacing.spaceDefault

                NavHost(
                        navController = navController,
                        startDestination = HomeScreenDestination
                ) {

                    appDestinations(
                            modifier = Modifier.padding(padding),
                            navController = navController
                    )
                }
            }
        }
    }
}
