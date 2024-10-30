package com.hasib.moneytrack

import android.content.res.Resources
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Stable
import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder
import com.hasib.moneytrack.helpers.snackbar.SnackBarManager
import com.hasib.moneytrack.helpers.snackbar.SnackBarMessage.Companion.toMessage
import com.hasib.moneytrack.navigation.Destination
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.launch

@Stable
class MoneyTrackAppState(
    val snackBarHostState: SnackbarHostState,
    private val snackBarManager: SnackBarManager,
    val navController: NavHostController,
    private val resources: Resources,
    coroutineScope: CoroutineScope
) {
    init {
        coroutineScope.launch {
            snackBarManager.snackBarMessages.filterNotNull().collect { snackBarMessage ->
                val text = snackBarMessage.toMessage(resources)
                snackBarHostState.showSnackbar(text)
                snackBarManager.clearSnackBarState()
            }
        }
    }

    fun popUp() {
        navController.popBackStack()
    }

    fun navigate(route: Destination, navOptions: NavOptionsBuilder.() -> Unit) {
        navController.navigate(route) {
            navOptions(this)
            launchSingleTop = true
        }
    }

    fun navigateAndPopUp(
        route: Destination,
        popUp: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        navController.navigate(route) {
            navOptions(this)
            launchSingleTop = true
            popUpTo(popUp) { inclusive = true }
        }
    }

    fun clearAndNavigate(route: Destination) {
        navController.navigate(route) {
            launchSingleTop = true
            popUpTo(0) { inclusive = true }
        }
    }
}