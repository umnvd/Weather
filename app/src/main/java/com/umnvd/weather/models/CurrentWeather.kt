package com.umnvd.weather.models

import java.util.*

data class CurrentWeather(
    val updateTime: Date,
    val city: City,
    val temp: Int
)
