package com.rohit.luasapp.repository.forecast

import com.rohit.luasapp.api.LuasService
import com.rohit.luasapp.model.ErrorForecastData
import com.rohit.luasapp.model.ForecastData
import com.rohit.luasapp.model.LoadedForecastData
import com.rohit.luasapp.model.LoadingForecastData
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class ForecastRepository @Inject constructor(
    private val luasService: LuasService
) : IForecastRepository {

    private val forecastSubject = BehaviorSubject.createDefault(LoadingForecastData as ForecastData)

    override val forecast: Observable<ForecastData> = forecastSubject

    override fun loadForecast(stopAbv: String): Completable =
        luasService.loadForecast(stopAbv)
            .doOnSubscribe { forecastSubject.onNext(LoadingForecastData) }
            .doOnSuccess { forecastSubject.onNext(
                LoadedForecastData(
                    it
                )
            ) }
            .doOnError { forecastSubject.onNext(
                ErrorForecastData(
                    it
                )
            ) }
            .ignoreElement()
}