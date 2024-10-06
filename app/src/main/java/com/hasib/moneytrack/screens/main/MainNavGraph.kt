package com.hasib.moneytrack.screens.main

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hasib.moneytrack.screens.add_record.AddRecordScreen
import com.hasib.moneytrack.screens.dashboard.DashboardScreen
import com.hasib.moneytrack.screens.preferences.PreferencesScreen

@Composable
fun MainNavGraph(
    drawerState: DrawerState,
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = MainNavigation.Dashboard.route
    ) {
        composable(MainNavigation.Dashboard.route) {
            DashboardScreen(
                drawerState = drawerState,
                parentNavController = navController
            )
        }
        composable(MainNavigation.AddRecord.route) {
            AddRecordScreen(navController = navController)
        }
        composable(MainNavigation.Preferences.route) {
            PreferencesScreen()
        }
    }
}