package com.umnvd.weather.screens.weather_forecast

import androidx.lifecycle.ViewModel
import com.umnvd.weather.data.cities.CitiesRepository
import com.umnvd.weather.data.weather.forecast.WeatherForecastRepository

class WeatherForecastViewModel(
    private val weatherForecastRepository: WeatherForecastRepository,
    private val citiesRepository: CitiesRepository,
): ViewModel() {

}