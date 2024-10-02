package com.hasib.moneytrack.screens.main

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hasib.moneytrack.screens.dashboard.DashboardScreen
import com.hasib.moneytrack.screens.preferences.PreferencesScreen

@Composable
fun DrawerNavGraph(
    drawerState: DrawerState,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = DrawerNavigation.Dashboard.route
    ) {
        composable(DrawerNavigation.Dashboard.route) {
            DashboardScreen(
                drawerState = drawerState,
            )
        }
        composable(DrawerNavigation.Preferences.route) {
            PreferencesScreen()
        }
    }
}