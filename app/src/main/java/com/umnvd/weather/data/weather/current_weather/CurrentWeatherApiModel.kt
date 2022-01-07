package com.umnvd.weather.data.weather.current_weather

import com.google.gson.annotations.SerializedName

data class CurrentWeatherNetworkModel(
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("main")
    val temp: Temp,
)

data class Temp(
    @SerializedName("temp")
    val temp: Double
)

data class Weather(
    @SerializedName("icon")
    val iconId: String
)