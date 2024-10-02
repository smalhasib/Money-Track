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
import androidx.compose.ui.graphics.vector.ImageVector

sealed class BottomNavigation(
    val title: String,
    val selectedIcon: ImageVector,
    val unselectedIcon: ImageVector,
    val route: String
) {
    data object Accounts : BottomNavigation(
        "Accounts",
        Icons.Filled.AccountBalanceWallet,
        Icons.Outlined.AccountBalanceWallet,
        "accounts"
    )

    data object Records : BottomNavigation(
        "Records",
        Icons.Filled.Receipt,
        Icons.Outlined.Receipt,
        "records"
    )

    data object Categories : BottomNavigation(
        "Categories",
        Icons.Filled.Sell,
        Icons.Outlined.Sell,
        "categories"
    )

    data object Analysis : BottomNavigation(
        "Analysis",
        Icons.Filled.Analytics,
        Icons.Outlined.Analytics,
        "analysis"
    )

    data object Budget : BottomNavigation(
        "Budget",
        Icons.Filled.Calculate,
        Icons.Outlined.Calculate,
        "budget"
    )
}