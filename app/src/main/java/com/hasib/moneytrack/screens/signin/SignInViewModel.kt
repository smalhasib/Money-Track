package com.hasib.moneytrack.screens.signin

import android.content.Context
import com.hasib.moneytrack.base.BaseViewModel
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.service.AccountService
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigatorService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(
    private val accountService: AccountService,
    navigatorService: NavigatorService,
    logService: LogService,
) : BaseViewModel(logService, navigatorService) {

    fun onGoogleSignInClick(context: Context) {
        launchCatching {
            accountService.authenticate(context)
            navigateAndPopUp(Destination.HomeGraph, Destination.AuthGraph)
        }
    }

}