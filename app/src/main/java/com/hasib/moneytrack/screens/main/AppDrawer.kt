package com.hasib.moneytrack.screens.main

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.compose.currentBackStackEntryAsState
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(drawerState: DrawerState, navController: NavController) {
    val drawerItems = listOf(
        DrawerNavigation.Preferences,
        DrawerNavigation.ExportRecords,
        DrawerNavigation.DeleteAndReset,
        DrawerNavigation.LikeMoneyTrack,
        DrawerNavigation.Help,
        DrawerNavigation.Feedback
    )
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination
    val coroutineScope = rememberCoroutineScope()

    ModalDrawerSheet {
        Spacer(modifier = Modifier.height(16.dp))
        Text(
            text = "MoneyTrack",
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 24.dp)
        )
        Text(
            text = "1.5.0",
            style = MaterialTheme.typography.bodyMedium,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(start = 24.dp)
        )
        HorizontalDivider(
            thickness = 2.dp,
            modifier = Modifier.padding(vertical = 8.dp)
        )
        repeat(8) { index ->
            when (index) {
                1 -> DrawerDivider("Management")
                4 -> DrawerDivider("Application")
                else -> {
                    val currentIndex = when {
                        index in 2..3 -> index - 1
                        index > 4 -> index - 2
                        else -> index
                    }
                    val screen = drawerItems[currentIndex]
                    val isSelected = currentDestination?.hierarchy?.any {
                        it.route == screen.route
                    } == true

                    NavigationDrawerItem(
                        label = {
                            Text(text = drawerItems[currentIndex].title)
                        },
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navController.navigate(drawerItems[currentIndex].route)
                            }
                        },
                        icon = {
                            Icon(
                                imageVector = if (isSelected) {
                                    drawerItems[currentIndex].selectedIcon
                                } else drawerItems[currentIndex].unselectedIcon,
                                contentDescription = drawerItems[currentIndex].title
                            )
                        },
                        modifier = Modifier
                            .padding(NavigationDrawerItemDefaults.ItemPadding)
                    )
                }
            }
        }
    }
}