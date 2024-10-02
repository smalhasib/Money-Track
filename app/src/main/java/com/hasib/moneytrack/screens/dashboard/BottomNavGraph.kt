package com.hasib.moneytrack.screens.dashboard

import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.hasib.moneytrack.screens.accounts.AccountsScreen
import com.hasib.moneytrack.screens.analysis.AnalysisScreen
import com.hasib.moneytrack.screens.budgets.BudgetsScreen
import com.hasib.moneytrack.screens.categories.CategoriesScreen
import com.hasib.moneytrack.screens.records.RecordsScreen

@Composable
fun BottomNavGraph(navController: NavHostController) {
    NavHost(
        navController = navController,
        startDestination = BottomNavigation.Records.route
    ) {
        composable(BottomNavigation.Records.route) {
            RecordsScreen()
        }
        composable(BottomNavigation.Analysis.route) {
            AnalysisScreen()
        }
        composable(BottomNavigation.Budget.route) {
            BudgetsScreen()
        }
        composable(BottomNavigation.Accounts.route) {
            AccountsScreen()
        }
        composable(BottomNavigation.Categories.route) {
            CategoriesScreen()
        }
    }
}