
package com.rohit.luasapp.api

import com.rohit.luasapp.model.StopInfo
import io.reactivex.rxjava3.core.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface LuasService {

    @GET("get.ashx?action=forecast&encrypt=false")
    fun loadForecast(@Query("stop") stopAbv: String): Single<StopInfo>

}
