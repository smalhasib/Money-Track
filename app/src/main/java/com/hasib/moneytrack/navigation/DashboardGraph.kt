package com.hasib.moneytrack.navigation

import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import com.hasib.moneytrack.screens.accounts.AccountsScreen
import com.hasib.moneytrack.screens.analysis.AnalysisScreen
import com.hasib.moneytrack.screens.budgets.BudgetsScreen
import com.hasib.moneytrack.screens.categories.CategoriesScreen
import com.hasib.moneytrack.screens.records.RecordsScreen

fun NavGraphBuilder.dashboardGraph() {
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