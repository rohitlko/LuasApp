package com.rohit.luasapp.model

/**
 * Sealed class to represent all the possible status types of the forecast data.
 *
 */
sealed class ForecastData

object LoadingForecastData : ForecastData()
class LoadedForecastData(val forecast: StopInfo): ForecastData()
class ErrorForecastData(val error: Throwable) : ForecastData()