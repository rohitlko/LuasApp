package com.rohit.luasapp.ui.forecast

import android.util.Log
import androidx.hilt.Assisted
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rohit.luasapp.model.ErrorForecastData
import com.rohit.luasapp.model.LoadedForecastData
import com.rohit.luasapp.model.LoadingForecastData
import com.rohit.luasapp.model.StopInfo
import com.rohit.luasapp.model.StopAbvEnum
import com.rohit.luasapp.repository.forecast.ForecastRepository
import com.rohit.luasapp.util.Response
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.disposables.Disposable
import java.time.LocalTime

class ForecastViewModel @ViewModelInject constructor(
    private val repository: ForecastRepository,
    @Assisted private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    // Disposable container that can hold multiple other Disposables, so later it can be cleaned
    // at once and avoid memory leak.
    private val compositeDisposable = CompositeDisposable()

    // Emits the most recent data to its observers
    val currentForecast: MutableLiveData<Response<StopInfo>> = MutableLiveData()

    /**
     * Initialize the ViewModel subscribing to the Forecast Observable from the repository and observes
     * the new data received.
     */
    fun initialize() {
        addToDisposable(
            repository.forecast
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        // Updates the LiveData variable with the new value received from the server
                        currentForecast.value = data
                            .let { it ->
                                when (it) {
                                    is LoadingForecastData -> Response.loading()
                                    is LoadedForecastData -> {
                                        // This is a request from the test, if the tram direction is to Stillorgan,
                                        // the Inbound direction should be used, otherwise use Outbound
                                        val tramDirection: String =
                                            if (it.forecast.stopAbbreviation == StopAbvEnum.STILLORGAN.abv) "Inbound" else "Outbound"

                                        // Filters the lines based on the tram direction
                                        it.forecast.lines =
                                            it.forecast.lines.filter { direction -> direction.name == tramDirection }

                                        Response.success(it.forecast)
                                    }
                                    is ErrorForecastData -> Response.error(
                                        it.error.localizedMessage,
                                        it.error
                                    )
                                }
                            }
                    },
                    {
                        // Updates the LiveData variable with an Error Response providing the message
                        currentForecast.value = Response.error(it.localizedMessage, it)
                    }
                )
        )
    }

    /**
     * Calls the repository to load the latest forecast of a stop.
     *
     * @param stopAbv
     */
    fun refreshForecast(stopAbv: String = "") {
        // If no stop name abbreviation is provided, a default stop name should be get based on the time.
        // This is a request from the test.
        val stopName = if (stopAbv == "") getStopName() else stopAbv

        addToDisposable(
            repository.loadForecast(stopName)
                .subscribe(
                    { Log.d("ForecastViewModel", "Success to refresh forecast") },
                    { Log.e("MainViewModel", "Error while refreshing forecast", it) }
                )
        )
    }

    /**
     * Returns a default stop name based on the current device time.
     * Should return 'MAR' if the time is between 00:00 and 12:00
     * else should return 'STI'
     */
    private fun getStopName() = when {
        LocalTime.now().isAfter(LocalTime.MIDNIGHT) && LocalTime.now()
            .isBefore(LocalTime.NOON) -> StopAbvEnum.MARLBOROUGH.abv
        else -> StopAbvEnum.STILLORGAN.abv
    }

    /**
     * Adds any new disposable to the compositeDisposable
     *
     * @param disposable
     */
    private fun addToDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    /**
     * Clears the CompositeDisposable when the ViewModel is destroyed.
     *
     */
    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}