package com.hasib.moneytrack.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerState
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.hasib.moneytrack.screens.main.MainNavigation

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    drawerState: DrawerState,
    parentNavController: NavController
) {
    val navController = rememberNavController()
    Scaffold(
        topBar = { DashboardAppBar(drawerState = drawerState) },
        bottomBar = { BottomBar(navController = navController) },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    parentNavController.navigate(MainNavigation.AddRecord.route)
                }
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Record"
                )
            }
        }
    ) { _ ->
        BottomNavGraph(navController = navController)
    }
}
