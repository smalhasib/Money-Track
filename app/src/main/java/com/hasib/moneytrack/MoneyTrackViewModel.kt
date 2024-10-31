package com.hasib.moneytrack

import com.hasib.moneytrack.base.BaseViewModel
import com.hasib.moneytrack.service.AccountService
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigationService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoneyTrackViewModel @Inject constructor(
    navigationService: NavigationService,
    accountService: AccountService,
    logService: LogService,
) : BaseViewModel(logService, navigationService) {
    val hasUser = accountService.hasUser
    val navigationActions = navigationService.navigationActions
}