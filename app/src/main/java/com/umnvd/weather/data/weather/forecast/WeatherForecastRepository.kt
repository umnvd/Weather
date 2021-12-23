package com.umnvd.weather.data.weather.forecast

import com.umnvd.weather.data.util.FlowResult
import com.umnvd.weather.model.City
import com.umnvd.weather.model.DayWeatherForecast

interface WeatherForecastRepository {

    fun getWeatherForecasts(city: City): FlowResult<List<DayWeatherForecast>>

}