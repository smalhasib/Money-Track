package com.hasib.moneytrack.di

import androidx.credentials.CredentialManager
import com.hasib.moneytrack.data.AppUserManager
import com.hasib.moneytrack.navigation.DefaultNavigator
import com.hasib.moneytrack.navigation.Navigator
import com.hasib.moneytrack.screens.addrecord.AddRecordViewModel
import com.hasib.moneytrack.screens.dashboard.DashboardViewModel
import com.hasib.moneytrack.screens.signin.SignInViewModel
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    single<Navigator> { DefaultNavigator() }
    single<CredentialManager> {
        CredentialManager.create(context = androidContext())
    }
    single<AppUserManager> {
        AppUserManager()
    }

    viewModelOf(::DashboardViewModel)
    viewModelOf(::AddRecordViewModel)
    viewModelOf(::SignInViewModel)
}