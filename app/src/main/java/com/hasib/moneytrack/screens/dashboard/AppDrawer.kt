package com.hasib.moneytrack.screens.dashboard

import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.filled.Logout
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.ImportExport
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.material3.DrawerState
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.hasib.moneytrack.models.NavigationItem
import com.hasib.moneytrack.navigation.Destination
import kotlinx.coroutines.launch

@Composable
fun AppDrawer(
    drawerState: DrawerState,
    navigateTo: (Destination) -> Unit,
    isRouteCurrentlySelected: (Destination) -> Boolean,
    logoutAction: () -> Unit
) {
    val drawerItems = listOf(
        NavigationItem(
            route = Destination.PreferencesScreen,
            title = "Preferences",
            selectedIcon = Icons.Filled.Settings,
            unselectedIcon = Icons.Outlined.Settings,
        ),
        NavigationItem(
            route = Destination.ExportRecordsScreen,
            title = "Export records",
            selectedIcon = Icons.Filled.ImportExport,
            unselectedIcon = Icons.Outlined.ImportExport,
        ),
        NavigationItem(
            route = Destination.DeleteAndResetScreen,
            title = "Delete & Reset",
            selectedIcon = Icons.Filled.Delete,
            unselectedIcon = Icons.Outlined.Delete,
        ),
        NavigationItem(
            route = Destination.LikeMoneyTrackScreen,
            title = "Like MoneyTrack",
            selectedIcon = Icons.Filled.ThumbUp,
            unselectedIcon = Icons.Outlined.ThumbUp,
        ),
        NavigationItem(
            route = Destination.HelpScreen,
            title = "Help",
            selectedIcon = Icons.AutoMirrored.Filled.Help,
            unselectedIcon = Icons.AutoMirrored.Outlined.Help,
        ),
        NavigationItem(
            route = Destination.FeedbackScreen,
            title = "Feedback",
            selectedIcon = Icons.Filled.Feedback,
            unselectedIcon = Icons.Outlined.Feedback,
        ),
    )

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
                    val isSelected = isRouteCurrentlySelected(screen.route)

                    NavigationDrawerItem(
                        label = {
                            Text(text = drawerItems[currentIndex].title)
                        },
                        selected = isSelected,
                        onClick = {
                            coroutineScope.launch {
                                drawerState.close()
                                navigateTo(screen.route)
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
        Spacer(modifier = Modifier.weight(1f))
        NavigationDrawerItem(
            label = {
                Text(text = "Logout")
            },
            selected = false,
            onClick = logoutAction,
            icon = {
                Icon(
                    imageVector = Icons.AutoMirrored.Filled.Logout,
                    contentDescription = "Logout"
                )
            },
            modifier = Modifier
                .padding(NavigationDrawerItemDefaults.ItemPadding)
        )
    }
}