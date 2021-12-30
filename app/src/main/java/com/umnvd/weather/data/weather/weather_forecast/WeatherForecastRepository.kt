package com.umnvd.weather.data.weather.weather_forecast

import com.umnvd.weather.models.City
import com.umnvd.weather.models.DayWeatherForecast
import kotlinx.coroutines.flow.Flow
import com.umnvd.weather.data.utils.Result

interface WeatherForecastRepository {

    fun getWeatherForecast(city: City): Flow<Result<List<DayWeatherForecast>>>

}