package com.umnvd.weather.models

data class CurrentWeather(
    val cityName: String,
    val iconUrl: String,
    val temp: Int
)
