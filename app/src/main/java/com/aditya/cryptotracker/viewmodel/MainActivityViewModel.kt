package com.aditya.cryptotracker.viewmodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.aditya.cryptotracker.model.Currency
import com.aditya.cryptotracker.model.CurrencyCombinedData
import com.aditya.cryptotracker.repository.CurrencyRepository
import com.aditya.cryptotracker.utils.AppUtils
import com.aditya.cryptotracker.utils.Constants
import com.aditya.cryptotracker.utils.NetworkManager
import com.aditya.cryptotracker.utils.Result
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException
import javax.inject.Inject
import kotlin.math.pow

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val currencyRepository: CurrencyRepository,
    private val appUtils: AppUtils,
    private val networkManager: NetworkManager
) : ViewModel() {

    private val _currencyCombinedData = MutableLiveData<Result<List<CurrencyCombinedData>>>()
    val currencyCombinedData: LiveData<Result<List<CurrencyCombinedData>>> get() = _currencyCombinedData

    private val _exchangeRates = MutableLiveData<List<Currency>>()
    val exchangeRates: LiveData<List<Currency>> get() = _exchangeRates

    private val _lastRefreshTime = MutableLiveData<String>()
    val lastRefreshTime: LiveData<String> get() = _lastRefreshTime

    private var isFetchingData = false

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        handleError(throwable)
        isFetchingData = false
    }

    init {
        fetchDataWithRetry()
        startAutoRefresh()
    }

    private fun startAutoRefresh() {
        viewModelScope.launch {
            while (true) {
                delay(Constants.REFRESH_INTERVAL_MILLIS)
                fetchDataWithRetry()
            }
        }
    }

    fun refreshData() {
        if (!isFetchingData) {
            fetchDataWithRetry()
        }
    }

    private fun fetchDataWithRetry() {
        viewModelScope.launch(coroutineExceptionHandler) {
            isFetchingData = true

            if (!networkManager.isNetworkAvailable()) {
                updateCurrency(Result.Error("No internet Connection"))
                isFetchingData = false
                return@launch
            }
            updateCurrency(Result.Loading)

            val result = withContext(Dispatchers.IO) {
                try {
                    val currencyListDeferred = async { currencyRepository.getCurrencyList() }
                    val exchangeRatesDeferred = async { currencyRepository.getExchangeRates() }

                    val currencyList = currencyListDeferred.await()
                    val exchangeRates = exchangeRatesDeferred.await()

                    val combinedDataList = exchangeRates.rates.keys.mapNotNull { symbol ->
                        val exchangeRate = exchangeRates.rates[symbol]
                        val roundedExchangeRate = appUtils.roundTo6DecimalPlaces(exchangeRate)
                        val currencyInfo = currencyList.crypto[symbol]

                        if (currencyInfo != null) {
                            CurrencyCombinedData(roundedExchangeRate, currencyInfo)
                        } else {
                            null
                        }
                    }

                    Result.Success(combinedDataList)
                } catch (e: Exception) {
                    handleError(e)
                    Result.Error("Error fetching data")
                }
            }

            updateCurrency(result)
            isFetchingData = false
            _lastRefreshTime.postValue("Last Refresh: ${appUtils.getCurrentDateTime()}")
        }
    }

    private suspend fun handleRetryDelay(retryCount: Int) {
        delay(1000 * (2.toDouble().pow(retryCount)).toLong())
    }

    private fun handleError(throwable: Throwable) {
        Log.e("ApiException", "Error fetching data: ${throwable.message}", throwable)
        val errorMessage = throwable.message ?: "Unknown Error"
        updateCurrency(Result.Error("Error fetching data: $errorMessage"))
        _lastRefreshTime.postValue("Error fetching data: $errorMessage")
    }

    private fun updateCurrency(result: Result<List<CurrencyCombinedData>>) {
        _currencyCombinedData.postValue(result)
    }
}
