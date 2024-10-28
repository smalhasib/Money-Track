package com.hasib.moneytrack.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import com.google.firebase.auth.FirebaseAuth
import com.hasib.moneytrack.helpers.extensions.ObserveAsEvents
import com.hasib.moneytrack.screens.addrecord.AddRecordScreen
import com.hasib.moneytrack.screens.dashboard.DashboardScreen
import com.hasib.moneytrack.screens.preferences.PreferencesScreen
import com.hasib.moneytrack.screens.signin.SignInScreen
import org.koin.compose.koinInject

@Composable
fun RootNavGraph() {
    val navController = rememberNavController()
    val navigator = koinInject<Navigator>()
    val auth = FirebaseAuth.getInstance()

    ObserveAsEvents(flow = navigator.navigationActions) { action ->
        when (action) {
            is NavigationAction.Navigate -> navController.navigate(
                action.destination
            ) {
                action.navOptions(this)
            }

            NavigationAction.NavigateUp -> navController.navigateUp()
        }
    }

    val startDestination = if (auth.currentUser != null) {
        Destination.HomeGraph
    } else {
        Destination.AuthGraph
    }

    NavHost(
        navController = navController,
        startDestination = startDestination,
    ) {
        navigation<Destination.AuthGraph>(
            startDestination = Destination.SignInScreen,
        ) {
            composable<Destination.SignInScreen> {
                SignInScreen()
            }
        }

        navigation<Destination.HomeGraph>(
            startDestination = Destination.DashboardScreen,
        ) {
            composable<Destination.DashboardScreen> { backStackEntry ->
                val currentDestination = backStackEntry.destination
                DashboardScreen(
                    isRouteCurrentlySelected = { destination ->
                        currentDestination.hierarchy.any {
                            it.route?.split('.')?.last() == destination.toString()
                        }
                    }
                )
            }
            composable<Destination.AddRecordScreen> {
                AddRecordScreen()
            }
            composable<Destination.PreferencesScreen> {
                PreferencesScreen()
            }
        }
    }
}