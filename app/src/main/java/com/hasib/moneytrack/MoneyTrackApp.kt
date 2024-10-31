package com.hasib.moneytrack

import android.annotation.SuppressLint
import android.app.Activity
import android.content.pm.ActivityInfo
import android.content.res.Resources
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.hasib.moneytrack.helpers.extensions.ObserveAsEvents
import com.hasib.moneytrack.helpers.snackbar.SnackBarManager
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.navigation.NavigationAction
import com.hasib.moneytrack.navigation.moneyTrackGraph
import com.hasib.moneytrack.ui.theme.MoneyTrackTheme
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow

@SuppressLint("SourceLockedOrientationActivity")
@Composable
fun MoneyTrackApp(
    viewModel: MoneyTrackViewModel = hiltViewModel(),
) {
    val activity = (LocalContext.current as Activity)
    activity.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_PORTRAIT

    MoneyTrackAppContent(
        hasUser = viewModel.hasUser,
        navigationActions = viewModel.navigationActions
    )
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MoneyTrackAppContent(
    hasUser: Boolean,
    navigationActions: Flow<NavigationAction>,
) {
    MoneyTrackTheme {
        Surface(
            color = MaterialTheme.colorScheme.background
        ) {
            val appState = rememberAppState()
            val startDestination = if (hasUser) {
                Destination.HomeGraph
            } else {
                Destination.AuthGraph
            }

            Scaffold(
                snackbarHost = {
                    SnackbarHost(appState.snackBarHostState) { snackBarData ->
                        Snackbar(snackBarData, contentColor = MaterialTheme.colorScheme.onPrimary)
                    }
                }
            ) { _ ->
                ObserveAsEvents(flow = navigationActions) { action ->
                    when (action) {
                        is NavigationAction.Navigate -> appState.navigate(
                            action.destination,
                            action.navOptions
                        )

                        NavigationAction.NavigatePopUp -> appState.popUp()

                        is NavigationAction.NavigateAndPopUp -> appState.navigateAndPopUp(
                            action.destination,
                            action.popUp,
                            action.navOptions
                        )

                        is NavigationAction.ClearAndNavigate -> appState.clearAndNavigate(
                            action.destination
                        )
                    }
                }
                NavHost(
                    navController = appState.navController,
                    startDestination = startDestination,
                ) {
                    moneyTrackGraph()
                }
            }
        }
    }
}

@Composable
fun rememberAppState(
    snackBarHostState: SnackbarHostState = SnackbarHostState(),
    snackBarManager: SnackBarManager = SnackBarManager,
    navController: NavHostController = rememberNavController(),
    resources: Resources = resources(),
    coroutineScope: CoroutineScope = rememberCoroutineScope()
) = remember(snackBarHostState, snackBarManager, navController, resources, coroutineScope) {
    MoneyTrackAppState(snackBarHostState, snackBarManager, navController, resources, coroutineScope)
}

@Composable
@ReadOnlyComposable
fun resources(): Resources = LocalContext.current.resources
