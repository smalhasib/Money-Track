package com.hasib.moneytrack.di

import com.hasib.moneytrack.service.NavigationService
import com.hasib.moneytrack.service.impl.NavigationServiceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AppServiceModule {

    @Binds
    @Singleton
    abstract fun provideNavigatorService(impl: NavigationServiceImpl): NavigationService
}