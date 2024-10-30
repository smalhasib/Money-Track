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
import androidx.compose.ui.res.stringResource
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.currentBackStackEntryAsState
import com.hasib.moneytrack.models.NavigationItem
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.R.string as AppText

@Composable
fun BottomBar(navController: NavController) {
    val navigationItems = listOf(
        NavigationItem(
            route = Destination.RecordsScreen,
            title = AppText.records,
            selectedIcon = Icons.Filled.Receipt,
            unselectedIcon = Icons.Outlined.Receipt,
        ),
        NavigationItem(
            route = Destination.AnalysisScreen,
            title = AppText.analysis,
            selectedIcon = Icons.Filled.Analytics,
            unselectedIcon = Icons.Outlined.Analytics,
        ),
        NavigationItem(
            route = Destination.BudgetsScreen,
            title = AppText.budgets,
            selectedIcon = Icons.Filled.Calculate,
            unselectedIcon = Icons.Outlined.Calculate,
        ),
        NavigationItem(
            route = Destination.AccountsScreen,
            title = AppText.accounts,
            selectedIcon = Icons.Filled.AccountBalanceWallet,
            unselectedIcon = Icons.Outlined.AccountBalanceWallet,
        ),
        NavigationItem(
            route = Destination.CategoriesScreen,
            title = AppText.categories,
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
                    Text(text = stringResource(screen.title))
                },
                icon = {
                    Icon(
                        imageVector = if (isSelected) screen.selectedIcon else screen.unselectedIcon,
                        contentDescription = stringResource(screen.title),
                        modifier = Modifier.rotate(
                            if (screen == navigationItems.last()) 90f else 0f
                        )
                    )
                }
            )
        }
    }
}