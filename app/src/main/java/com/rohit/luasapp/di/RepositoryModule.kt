package com.rohit.luasapp.di

import com.rohit.luasapp.api.LuasService
import com.rohit.luasapp.repository.forecast.ForecastRepository
import com.rohit.luasapp.repository.forecast.IForecastRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module

@InstallIn(ViewModelComponent::class)
object RepositoryModule {

    @Provides
    @ViewModelScoped
    fun provideForecastRepository(luasService: LuasService): IForecastRepository = ForecastRepository(luasService)

}