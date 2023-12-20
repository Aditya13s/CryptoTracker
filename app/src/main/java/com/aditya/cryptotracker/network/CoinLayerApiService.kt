package com.aditya.cryptotracker.network

import com.aditya.cryptotracker.model.CurrencyListResponse
import com.aditya.cryptotracker.model.ExchangeRateResponse
import com.aditya.cryptotracker.utils.Constants
import retrofit2.http.GET
import retrofit2.http.Query

interface CoinLayerApiService {

    @GET("list")
    suspend fun getCurrencyList(@Query("access_key") apiKey: String = Constants.API_KEY): CurrencyListResponse

    @GET("live")
    suspend fun getExchangeRates(@Query("access_key") apiKey: String = Constants.API_KEY): ExchangeRateResponse

}
