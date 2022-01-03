package com.umnvd.weather.screens.weather_forecast.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.umnvd.weather.databinding.PageForecastBinding
import com.umnvd.weather.models.DayWeatherForecast
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastAdapter: RecyclerView.Adapter<ForecastPageViewHolder>() {

    private var forecast: MutableList<DayWeatherForecast> = mutableListOf()

    fun setWeatherForecast(newForecast: List<DayWeatherForecast>) {
        val callback = WeatherForecastDiffCallback(forecast, newForecast)
        val diff = DiffUtil.calculateDiff(callback)
        diff.dispatchUpdatesTo(this)
        this.forecast = newForecast.toMutableList()
    }

    fun getPageTitle(position: Int): String {
        val dateFormat = SimpleDateFormat("EEE, dd.MM", Locale.getDefault())
        return dateFormat.format(forecast[position].date)
    }

    override fun getItemCount(): Int {
        return forecast.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ForecastPageViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = PageForecastBinding.inflate(inflater, parent, false)
        return ForecastPageViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ForecastPageViewHolder, position: Int) {
        holder.bind(forecast[position])
    }
}