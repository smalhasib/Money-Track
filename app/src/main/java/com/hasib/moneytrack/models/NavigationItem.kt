package com.hasib.moneytrack.models

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Dashboard
import androidx.compose.material.icons.outlined.Dashboard
import androidx.compose.ui.graphics.vector.ImageVector
import com.hasib.moneytrack.navigation.Destination

data class NavigationItem(
    val route: Destination,
    val title: String,
    val selectedIcon: ImageVector = Icons.Filled.Dashboard,
    val unselectedIcon: ImageVector = Icons.Outlined.Dashboard,
)
