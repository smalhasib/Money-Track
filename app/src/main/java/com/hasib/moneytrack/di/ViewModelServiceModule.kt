package com.hasib.moneytrack.di

import com.hasib.moneytrack.service.AccountService
import com.hasib.moneytrack.service.LogService
import com.hasib.moneytrack.service.impl.AccountServiceImpl
import com.hasib.moneytrack.service.impl.LogServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelServiceModule {

    @Binds
    @ViewModelScoped
    abstract fun provideLogService(impl: LogServiceImpl): LogService

    @Binds
    @ViewModelScoped
    abstract fun provideAccountService(impl: AccountServiceImpl): AccountService
}