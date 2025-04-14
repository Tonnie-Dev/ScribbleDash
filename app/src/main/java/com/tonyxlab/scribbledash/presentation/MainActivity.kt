package com.tonyxlab.scribbledash.presentation

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHost
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.tonyxlab.scribbledash.navigation.Destinations
import com.tonyxlab.scribbledash.navigation.Destinations.*
import com.tonyxlab.scribbledash.navigation.appDestinations
import com.tonyxlab.scribbledash.presentation.core.utils.spacing
import com.tonyxlab.scribbledash.presentation.theme.ScribbleDashTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

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
