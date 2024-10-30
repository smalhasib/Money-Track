package com.hasib.moneytrack.screens.dashboard

import com.hasib.moneytrack.base.BaseViewModel
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.service.AccountService
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigatorService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DashboardViewModel @Inject constructor(
    private val accountService: AccountService,
    logService: LogService,
    navigatorService: NavigatorService,
) : BaseViewModel(logService, navigatorService) {

    fun onDrawerItemClick(destination: Destination) = navigate(destination)

    fun onAddRecordClick() = navigate(Destination.AddRecordScreen)

    fun onSignOutClick() = launchCatching {
        accountService.signOut()
        clearAndNavigate(Destination.SignInScreen)
    }
}