package com.aditya.cryptotracker.model


import com.google.gson.annotations.SerializedName

data class CurrencyListResponse(
    @SerializedName("crypto")
    val crypto: Map<String, Currency>,
    @SerializedName("fiat")
    val fiat: Map<String, String>,
    @SerializedName("success")
    val success: Boolean
)