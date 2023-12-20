package com.aditya.cryptotracker.repository

import com.aditya.cryptotracker.model.CurrencyListResponse
import com.aditya.cryptotracker.model.ExchangeRateResponse
import com.aditya.cryptotracker.network.CoinLayerApiService
import javax.inject.Inject

class CurrencyRepository @Inject constructor(private val coinLayerApiService: CoinLayerApiService) {

    suspend fun getCurrencyList(): CurrencyListResponse {
        return coinLayerApiService.getCurrencyList()
    }

    suspend fun getExchangeRates(): ExchangeRateResponse {
        return coinLayerApiService.getExchangeRates()
    }
}
