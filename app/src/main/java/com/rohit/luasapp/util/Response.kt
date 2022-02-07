package com.rohit.luasapp.util

data class Response<out T>(val status: Status, val data: T?, val error: Throwable?, val message: String?) {

    enum class Status {
        SUCCESS,
        ERROR,
        LOADING
    }

    companion object {
        fun <T> success(data: T): Response<T> {
            return Response(Status.SUCCESS, data, null, null)
        }

        fun <T> error(message: String?, data: Throwable? = null): Response<T> {
            return Response(Status.ERROR, null, data, message)
        }

        fun <T> loading(): Response<T> {
            return Response(Status.LOADING, null, null, null)
        }
    }
}