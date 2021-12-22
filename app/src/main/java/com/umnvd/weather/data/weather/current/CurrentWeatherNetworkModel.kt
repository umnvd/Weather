package com.umnvd.weather.data.weather.current

import com.google.gson.annotations.SerializedName

data class CurrentWeatherNetworkModel(
    @SerializedName("main")
    val temp: CurrentTemp,
)
data class CurrentTemp(
    @SerializedName("temp")
    val temp: Double
)