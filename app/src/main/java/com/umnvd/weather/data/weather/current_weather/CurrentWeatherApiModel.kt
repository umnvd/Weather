package com.umnvd.weather.data.weather.current_weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherNetworkModel(
    @SerializedName("main")
    val tempData: TempData,
)
data class TempData(
    @SerializedName("temp")
    val temp: Double
)