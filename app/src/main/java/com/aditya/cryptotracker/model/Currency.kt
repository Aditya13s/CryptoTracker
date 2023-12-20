package com.aditya.cryptotracker.model


import com.google.gson.annotations.SerializedName

data class Currency(
    @SerializedName("icon_url")
    val iconUrl: String,
    @SerializedName("max_supply")
    val maxSupply: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("name_full")
    val nameFull: String,
    @SerializedName("symbol")
    val symbol: String
)