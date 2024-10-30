package com.hasib.moneytrack.service

import androidx.navigation.NavOptionsBuilder
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.navigation.NavigationAction
import kotlinx.coroutines.flow.Flow

interface NavigatorService {
    val navigationActions: Flow<NavigationAction>

    suspend fun navigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )

    suspend fun navigateUp()

    suspend fun navigateAndPopUp(
        destination: Destination,
        popUp: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {},
    )

    suspend fun clearAndNavigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit = {}
    )
}
