package com.hasib.moneytrack.screens.dashboard

import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState

@Composable
fun BottomBar(navController: NavController) {
    val navigationItems = listOf(
        BottomNavigation.Records,
        BottomNavigation.Analysis,
        BottomNavigation.Budget,
        BottomNavigation.Accounts,
        BottomNavigation.Categories
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        navigationItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route == screen.route
            } == true

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(screen.route)
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.findStartDestination().id)
                        launchSingleTop = true
                    }
                },
                label = {
                    Text(text = screen.title)
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = screen.title,
                        modifier = Modifier.rotate(
                            if (screen == navigationItems.last()) 90f else 0f
                        )
                    )
                }
            )
        }
    }
}