
package com.rohit.luasapp.api

import com.rohit.luasapp.model.StopInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * Interface responsible to hold all the API endpoints
 */
interface LuasService {

    /**
     * Endpoint to get the tram forecast of a specific stop by the stop name abbreviation.
     * Use of @Query annotation in the function arguments to dynamically append
     * the stopAbv into the URL.
     */
    @GET("get.ashx?action=forecast&encrypt=false")
    fun loadForecast(@Query("stop") stopAbv: String): Single<StopInfo>

}
