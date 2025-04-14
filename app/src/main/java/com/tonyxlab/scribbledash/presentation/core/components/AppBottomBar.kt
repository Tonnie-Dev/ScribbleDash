package com.tonyxlab.scribbledash.presentation.core.components

import android.R.attr.onClick
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.BottomAppBarDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.compose.material3.NavigationBarItem
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import com.tonyxlab.scribbledash.navigation.BottomNavigationOptions

@Composable
fun AppBottomBar(navController: NavController) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val currentHierarchy = navBackStackEntry?.destination?.hierarchy

    BottomAppBar(containerColor = MaterialTheme.colorScheme.surface) {
        BottomNavigationOptions.entries.forEachIndexed { i, navItem ->

            val isSelected by remember(currentRoute) {

                derivedStateOf {
                    currentHierarchy?.any { it.hasRoute(navItem.route::class) } != false
                }
            }

            NavigationBarItem(
                    selected = isSelected,
                    icon = {
                        Icon(
                                painter = painterResource(navItem.icon),
                                contentDescription = navItem.label,
                                tint = if (isSelected)
                                    MaterialTheme.colorScheme.primary
                                else
                                MaterialTheme  .colorScheme.inverseSurface

                        )
                    },
                    onClick = {
                        navController.navigate(navItem.route)
                    }
            )
        }
    }
}


