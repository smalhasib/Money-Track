package com.hasib.moneytrack.navigation

import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.hasib.moneytrack.screens.addrecord.AddRecordScreen
import com.hasib.moneytrack.screens.dashboard.DashboardScreen
import com.hasib.moneytrack.screens.preferences.PreferencesScreen
import com.hasib.moneytrack.screens.signin.SignInScreen

fun NavGraphBuilder.moneyTrackGraph() {
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
            val hierarchy = backStackEntry.destination.hierarchy
            DashboardScreen(
                isRouteCurrentlySelected = { destination ->
                    hierarchy.any {
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