package com.hasib.moneytrack.screens.main

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Help
import androidx.compose.material.icons.automirrored.outlined.Help
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Feedback
import androidx.compose.material.icons.filled.ImportExport
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.material.icons.outlined.Delete
import androidx.compose.material.icons.outlined.Feedback
import androidx.compose.material.icons.outlined.ImportExport
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.ThumbUp
import androidx.compose.ui.graphics.vector.ImageVector

sealed class DrawerNavigation(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    data object Dashboard : DrawerNavigation(
        title = "Dashboard",
        selectedIcon = Icons.Filled.Dashboard,
        unselectedIcon = Icons.Outlined.Dashboard,
        "dashboard"
    )

    data object Preferences : DrawerNavigation(
        title = "Preferences",
        selectedIcon = Icons.Filled.Settings,
        unselectedIcon = Icons.Outlined.Settings,
        "preferences"
    )

    data object ExportRecords : DrawerNavigation(
        title = "Export records",
        selectedIcon = Icons.Filled.ImportExport,
        unselectedIcon = Icons.Outlined.ImportExport,
        "export-records"
    )

    data object DeleteAndReset : DrawerNavigation(
        title = "Delete & Reset",
        selectedIcon = Icons.Filled.Delete,
        unselectedIcon = Icons.Outlined.Delete,
        "delete-reset"
    )

    data object LikeMoneyTrack : DrawerNavigation(
        title = "Like MoneyTrack",
        selectedIcon = Icons.Filled.ThumbUp,
        unselectedIcon = Icons.Outlined.ThumbUp,
        "like-moneytrack"
    )

    data object Help : DrawerNavigation(
        title = "Help",
        selectedIcon = Icons.AutoMirrored.Filled.Help,
        unselectedIcon = Icons.AutoMirrored.Outlined.Help,
        "help"
    )

    data object Feedback : DrawerNavigation(
        title = "Feedback",
        selectedIcon = Icons.Filled.Feedback,
        unselectedIcon = Icons.Outlined.Feedback,
        "feedback"
    )
}