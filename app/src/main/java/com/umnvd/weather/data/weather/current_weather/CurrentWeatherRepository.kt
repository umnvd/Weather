package com.umnvd.weather.data.weather.current_weather

import com.umnvd.weather.data.utils.FinalResult
import com.umnvd.weather.models.City
import com.umnvd.weather.models.CurrentWeather

interface CurrentWeatherRepository {

    suspend fun getCurrentWeather(city: City): FinalResult<CurrentWeather>

}