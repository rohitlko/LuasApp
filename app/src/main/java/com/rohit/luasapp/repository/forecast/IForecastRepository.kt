package com.rohit.luasapp.repository.forecast

import com.rohit.luasapp.model.ForecastData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable

/**
 * Forecast Repository Interface responsible to hold all available methods.
 *
 */
interface IForecastRepository {

    val forecast: Observable<ForecastData>

    fun loadForecast(stopAbv: String): Completable

}