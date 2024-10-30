package com.hasib.moneytrack.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.navigation.dashboardGraph
import com.hasib.moneytrack.R.string as AppText

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    isRouteCurrentlySelected: (Destination) -> Boolean,
    viewModel: DashboardViewModel = hiltViewModel(),
) {
    DashboardScreenContent(
        isRouteCurrentlySelected = isRouteCurrentlySelected,
        onDrawerItemClick = viewModel::onDrawerItemClick,
        onSignOutClick = viewModel::onSignOutClick,
        onAddRecordClick = viewModel::onAddRecordClick
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreenContent(
    isRouteCurrentlySelected: (Destination) -> Boolean,
    onDrawerItemClick: (Destination) -> Unit,
    onSignOutClick: () -> Unit,
    onAddRecordClick: () -> Unit,
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                drawerState = drawerState,
                onDrawerItemClick = { destination -> onDrawerItemClick(destination) },
                isRouteCurrentlySelected = isRouteCurrentlySelected,
                onSignOutClick = onSignOutClick
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { DashboardAppBar(drawerState = drawerState) },
            bottomBar = { BottomBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = onAddRecordClick
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = stringResource(AppText.add_record)
                    )
                }
            }
        ) { innerPadding ->
            NavHost(
                navController = navController,
                startDestination = Destination.RecordsScreen,
                modifier = Modifier.padding(innerPadding)
            ) {
                dashboardGraph()
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DashboardScreenPreview() {
    DashboardScreenContent(
        isRouteCurrentlySelected = { false },
        onDrawerItemClick = { },
        onSignOutClick = { },
        onAddRecordClick = { }
    )
}
