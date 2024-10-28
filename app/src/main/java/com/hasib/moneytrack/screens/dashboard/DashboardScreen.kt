package com.hasib.moneytrack.screens.dashboard

import android.annotation.SuppressLint
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.screens.accounts.AccountsScreen
import com.hasib.moneytrack.screens.analysis.AnalysisScreen
import com.hasib.moneytrack.screens.budgets.BudgetsScreen
import com.hasib.moneytrack.screens.categories.CategoriesScreen
import com.hasib.moneytrack.screens.records.RecordsScreen
import org.koin.androidx.compose.koinViewModel

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun DashboardScreen(
    viewModel: DashboardViewModel = koinViewModel(),
    isRouteCurrentlySelected: (Destination) -> Boolean,
) {
    val navController = rememberNavController()
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    ModalNavigationDrawer(
        drawerContent = {
            AppDrawer(
                drawerState = drawerState,
                navigateTo = { destination ->
                    viewModel.navigateTo(destination)
                },
                isRouteCurrentlySelected = isRouteCurrentlySelected,
                logoutAction = { viewModel.signOut() }
            )
        },
        drawerState = drawerState
    ) {
        Scaffold(
            topBar = { DashboardAppBar(drawerState = drawerState) },
            bottomBar = { BottomBar(navController = navController) },
            floatingActionButton = {
                FloatingActionButton(
                    onClick = {}
                ) {
                    Icon(
                        imageVector = Icons.Default.Add,
                        contentDescription = "Add Record"
                    )
                }
            }
        ) { _ ->
            NavHost(
                navController = navController,
                startDestination = Destination.RecordsScreen
            ) {
                composable<Destination.RecordsScreen> {
                    RecordsScreen()
                }
                composable<Destination.AnalysisScreen> {
                    AnalysisScreen()
                }
                composable<Destination.BudgetsScreen> {
                    BudgetsScreen()
                }
                composable<Destination.AccountsScreen> {
                    AccountsScreen()
                }
                composable<Destination.CategoriesScreen> {
                    CategoriesScreen()
                }
            }
        }
    }
}
