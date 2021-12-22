package com.umnvd.weather.model

import java.util.*

data class CurrentWeather(
    val updateTime: Date,
    val city: City,
    val temp: Int
)
