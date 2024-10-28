package com.hasib.moneytrack.navigation

import kotlinx.serialization.Serializable

sealed interface Destination {
    // Auth Graph
    @Serializable
    data object AuthGraph : Destination

    @Serializable
    data object SignInScreen : Destination

    // Main Graph
    @Serializable
    data object HomeGraph : Destination

    @Serializable
    data object DashboardScreen : Destination

    @Serializable
    data object AddRecordScreen : Destination

    @Serializable
    data object PreferencesScreen : Destination

    @Serializable
    data object ExportRecordsScreen : Destination

    @Serializable
    data object DeleteAndResetScreen : Destination

    @Serializable
    data object LikeMoneyTrackScreen : Destination

    @Serializable
    data object HelpScreen : Destination

    @Serializable
    data object FeedbackScreen : Destination

    // Bottom Nav Graph
    @Serializable
    data object RecordsScreen : Destination

    @Serializable
    data object AnalysisScreen : Destination

    @Serializable
    data object BudgetsScreen : Destination

    @Serializable
    data object AccountsScreen : Destination

    @Serializable
    data object CategoriesScreen : Destination
}
