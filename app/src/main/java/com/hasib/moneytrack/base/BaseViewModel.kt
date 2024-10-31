package com.hasib.moneytrack.base

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hasib.moneytrack.common.snackbar.SnackBarManager
import com.hasib.moneytrack.common.snackbar.SnackBarMessage.Companion.toSnackBarMessage
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.NavigationService
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

open class BaseViewModel(
    private val logService: LogService,
    private val navigationService: NavigationService
) : ViewModel() {
    fun launchCatching(snackBar: Boolean = true, block: suspend CoroutineScope.() -> Unit) =
        viewModelScope.launch(
            CoroutineExceptionHandler { _, throwable ->
                if (snackBar) {
                    SnackBarManager.showMessage(throwable.toSnackBarMessage())
                }
                logService.logNonFatalCrash(throwable)
            },
            block = block
        )

    fun navigateAndPopUp(destination: Destination, popUp: Destination) {
        viewModelScope.launch {
            navigationService.navigateAndPopUp(destination, popUp)
        }
    }

    fun navigate(destination: Destination) {
        viewModelScope.launch {
            navigationService.navigateTo(destination)
        }
    }

    fun popUp() {
        viewModelScope.launch {
            navigationService.navigateUp()
        }
    }

    suspend fun clearAndNavigate(destination: Destination) {
        viewModelScope.launch {
            navigationService.clearAndNavigate(destination)
        }
    }
}