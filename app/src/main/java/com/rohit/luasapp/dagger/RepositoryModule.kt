package com.rohit.luasapp.dagger

import com.rohit.luasapp.api.LuasService
import com.rohit.luasapp.repository.forecast.LuasRepository
import com.rohit.luasapp.repository.forecast.LuasInterface
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
    fun provideForecastRepository(luasService: LuasService): LuasInterface = LuasRepository(luasService)

}