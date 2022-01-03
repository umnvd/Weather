package com.umnvd.weather.screens.weather_forecast.adapters

import androidx.recyclerview.widget.DiffUtil
import com.umnvd.weather.models.DayWeatherForecast

class WeatherForecastDiffCallback(
    private val oldForecast: List<DayWeatherForecast>,
    private val newForecast: List<DayWeatherForecast>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldForecast.size

    override fun getNewListSize(): Int = newForecast.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldForecast[oldItemPosition].date == newForecast[newItemPosition].date
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldForecast[oldItemPosition] == newForecast[newItemPosition]
    }
}