package com.rohit.luasapp.api

import com.rohit.luasapp.data.StopInfo

data class ApiResponse<out T>(val status: Status, val data: T?, val error: Throwable?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): ApiResponse<T> {
            return ApiResponse(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String?, data: Throwable? = null): ApiResponse<T> {
            return ApiResponse(Status.ERROR, null, data, message)
        }

        fun <T> loading(): ApiResponse<T> {
            return ApiResponse(Status.LOADING, null, null, null)
        }
    }
}

sealed class LuasData

object LoadingForecastData : LuasData()
class LoadedForecastData(val forecast: StopInfo): LuasData()
class ErrorForecastData(val error: Throwable) : LuasData()