package com.hasib.moneytrack.screens.dashboard

import androidx.credentials.ClearCredentialStateRequest
import androidx.credentials.CredentialManager
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.auth.FirebaseAuth
import com.hasib.moneytrack.navigation.Destination
import com.hasib.moneytrack.navigation.Navigator
import kotlinx.coroutines.launch

class DashboardViewModel(
    private val credentialManager: CredentialManager,
    private val navigator: Navigator
) : ViewModel() {

    fun navigateTo(destination: Destination) {
        viewModelScope.launch {
            navigator.navigateTo(destination)
        }
    }

    fun signOut() {
        viewModelScope.launch {
            credentialManager.clearCredentialState(ClearCredentialStateRequest())
            FirebaseAuth.getInstance().signOut()
            navigator.navigateTo(Destination.AuthGraph)
        }
    }
}