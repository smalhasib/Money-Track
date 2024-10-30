package com.hasib.moneytrack

import com.hasib.moneytrack.base.BaseViewModel
import com.hasib.moneytrack.service.AccountService
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigatorService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MoneyTrackViewModel @Inject constructor(
    navigatorService: NavigatorService,
    accountService: AccountService,
    logService: LogService,
) : BaseViewModel(logService, navigatorService) {
    val hasUser = accountService.hasUser
    val navigationActions = navigatorService.navigationActions
}