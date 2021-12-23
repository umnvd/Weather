package com.umnvd.weather.data.weather.current

import com.umnvd.weather.data.util.FinalResult
import com.umnvd.weather.model.City
import com.umnvd.weather.model.CurrentWeather

interface CurrentWeatherRepository {

    suspend fun getCurrentWeather(city: City): FinalResult<CurrentWeather>

}