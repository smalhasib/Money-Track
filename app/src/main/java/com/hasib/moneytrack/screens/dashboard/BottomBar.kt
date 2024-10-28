package com.hasib.moneytrack.screens.dashboard

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBalanceWallet
import androidx.compose.material.icons.filled.Analytics
import androidx.compose.material.icons.filled.Calculate
import androidx.compose.material.icons.filled.Receipt
import androidx.compose.material.icons.filled.Sell
import androidx.compose.material.icons.outlined.AccountBalanceWallet
import androidx.compose.material.icons.outlined.Analytics
import androidx.compose.material.icons.outlined.Calculate
import androidx.compose.material.icons.outlined.Receipt
import androidx.compose.material.icons.outlined.Sell
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
import com.hasib.moneytrack.models.NavigationItem
import com.hasib.moneytrack.navigation.Destination
import timber.log.Timber

@Composable
fun BottomBar(navController: NavController) {
    val navigationItems = listOf(
        NavigationItem(
            route = Destination.RecordsScreen,
            title = "Records",
            selectedIcon = Icons.Filled.Receipt,
            unselectedIcon = Icons.Outlined.Receipt,
        ),
        NavigationItem(
            route = Destination.AnalysisScreen,
            title = "Analysis",
            selectedIcon = Icons.Filled.Analytics,
            unselectedIcon = Icons.Outlined.Analytics,
        ),
        NavigationItem(
            route = Destination.BudgetsScreen,
            title = "Budget",
            selectedIcon = Icons.Filled.Calculate,
            unselectedIcon = Icons.Outlined.Calculate,
        ),
        NavigationItem(
            route = Destination.AccountsScreen,
            title = "Accounts",
            selectedIcon = Icons.Filled.AccountBalanceWallet,
            unselectedIcon = Icons.Outlined.AccountBalanceWallet,
        ),
        NavigationItem(
            route = Destination.CategoriesScreen,
            title = "Categories",
            selectedIcon = Icons.Filled.Sell,
            unselectedIcon = Icons.Outlined.Sell,
        ),
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    NavigationBar {
        navigationItems.forEach { screen ->
            val isSelected = currentDestination?.hierarchy?.any {
                it.route?.split('.')?.last() == screen.route.toString()
            } == true
            Timber.d("BottomBar: isSelected: $isSelected")

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