package com.umnvd.weather.data.weather.current_weather

import com.umnvd.weather.models.CurrentWeather

interface CurrentWeatherNotifications {

    fun show(currentWeather: CurrentWeather)

}