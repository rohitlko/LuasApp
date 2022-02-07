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

class LuasViewModel @ViewModelInject constructor(
    private val repository: ForecastRepository
) : ViewModel() {

    private val compositeDisposable = CompositeDisposable()

    val currentForecast: MutableLiveData<Response<StopInfo>> = MutableLiveData()

    fun initialize() {
        addToDisposable(
            repository.forecast
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { data ->
                        currentForecast.value = data
                            .let { it ->
                                when (it) {
                                    is LoadingForecastData -> Response.loading()
                                    is LoadedForecastData -> {
                                        val tramDirection: String =
                                            if (it.forecast.stopAbbreviation == StopAbvEnum.STILLORGAN.abv) "Inbound" else "Outbound"

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
                        currentForecast.value = Response.error(it.localizedMessage, it)
                    }
                )
        )
    }

    fun refreshForecast(stopAbv: String = "") {
        val stopName = if (stopAbv == "") getStopName() else stopAbv

        addToDisposable(
            repository.loadForecast(stopName)
                .subscribe(
                    { Log.d("ForecastViewModel", "Success to refresh forecast") },
                    { Log.e("MainViewModel", "Error while refreshing forecast", it) }
                )
        )
    }

    private fun getStopName() = when {
        LocalTime.now().isAfter(LocalTime.MIDNIGHT) && LocalTime.now()
            .isBefore(LocalTime.NOON) -> StopAbvEnum.MARLBOROUGH.abv
        else -> StopAbvEnum.STILLORGAN.abv
    }

    private fun addToDisposable(disposable: Disposable) = compositeDisposable.add(disposable)

    override fun onCleared() {
        compositeDisposable.clear()
        super.onCleared()
    }
}