package com.hasib.moneytrack.di

import com.hasib.moneytrack.service.NavigatorService
import com.hasib.moneytrack.service.impl.NavigatorServiceImpl
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
    abstract fun provideNavigatorService(impl: NavigatorServiceImpl): NavigatorService
}