package com.hasib.moneytrack.navigation

import androidx.navigation.NavOptionsBuilder

sealed interface NavigationAction {

    data class Navigate(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {}
    ) : NavigationAction

    data object NavigatePopUp : NavigationAction

    data class NavigateAndPopUp(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {},
        val popUp: Destination
    ) : NavigationAction

    data class ClearAndNavigate(
        val destination: Destination,
        val navOptions: NavOptionsBuilder.() -> Unit = {},
    ) : NavigationAction
}