package com.aditya.cryptotracker.model


import com.google.gson.annotations.SerializedName

data class ExchangeRateResponse(
    @SerializedName("privacy")
    val privacy: String,
    @SerializedName("rates")
    val rates: Map<String, String>,
    @SerializedName("success")
    val success: Boolean,
    @SerializedName("target")
    val target: String,
    @SerializedName("terms")
    val terms: String,
    @SerializedName("timestamp")
    val timestamp: Int
)