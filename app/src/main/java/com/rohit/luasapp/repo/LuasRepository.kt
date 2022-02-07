package com.rohit.luasapp.repository.forecast

import com.rohit.luasapp.api.*
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class LuasRepository @Inject constructor(
    private val luasService: LuasService
) : LuasInterface {

    private val forecastSubject = BehaviorSubject.createDefault(LoadingForecastData as LuasData)

    override val forecast: Observable<LuasData> = forecastSubject

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

interface LuasInterface {
    val forecast: Observable<LuasData>
    fun loadForecast(stopAbv: String): Completable

}