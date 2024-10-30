package com.hasib.moneytrack.service.impl

import androidx.navigation.NavOptionsBuilder
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.navigation.NavigationAction
import com.hasib.moneytrack.service.NavigatorService
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import timber.log.Timber
import javax.inject.Inject

class NavigatorServiceImpl @Inject constructor() : NavigatorService {
    private val _navigationActions = Channel<NavigationAction>()
    override val navigationActions: Flow<NavigationAction> = _navigationActions.receiveAsFlow()

    @OptIn(ExperimentalCoroutinesApi::class, DelicateCoroutinesApi::class)
    override suspend fun navigateTo(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        Timber.d(_navigationActions.isEmpty.toString())
        Timber.d(_navigationActions.isClosedForSend.toString())
        Timber.d(_navigationActions.isClosedForReceive.toString())
        _navigationActions.send(
            NavigationAction.Navigate(
                destination = destination,
                navOptions = navOptions
            )
        )
    }

    override suspend fun navigateUp() {
        _navigationActions.send(NavigationAction.NavigatePopUp)
    }

    override suspend fun navigateAndPopUp(
        destination: Destination,
        popUp: Destination,
        navOptions: NavOptionsBuilder.() -> Unit,
    ) {
        _navigationActions.send(
            NavigationAction.NavigateAndPopUp(
                destination = destination,
                navOptions = navOptions,
                popUp = popUp
            )
        )
    }

    override suspend fun clearAndNavigate(
        destination: Destination,
        navOptions: NavOptionsBuilder.() -> Unit
    ) {
        _navigationActions.send(
            NavigationAction.ClearAndNavigate(
                destination = destination,
                navOptions = navOptions
            )
        )
    }
}