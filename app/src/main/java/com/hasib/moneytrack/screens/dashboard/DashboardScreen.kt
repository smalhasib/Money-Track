package com.hasib.moneytrack.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.material3.DrawerState
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.compose.rememberNavController

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    drawerState: DrawerState,
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { AppBar(drawerState = drawerState) },
        bottomBar = { BottomBar(navController = navController) }
    ) { _ ->
        BottomNavGraph(navController = navController)
    }
}
