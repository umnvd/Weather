package com.umnvd.weather.screens.weather_forecast.adapters

import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.umnvd.weather.R
import com.umnvd.weather.databinding.PageForecastBinding
import com.umnvd.weather.models.DayWeatherForecast
import java.text.SimpleDateFormat
import java.util.*

class ForecastPageViewHolder(
    private val binding: PageForecastBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(forecast: DayWeatherForecast) = with(binding) {
        Glide.with(weatherIconImageView.context)
            .load(forecast.iconUrl)
            .diskCacheStrategy(DiskCacheStrategy.RESOURCE)
            .fitCenter()
            .placeholder(R.drawable.weather_placeholder)
            .error(R.drawable.weather_placeholder)
            .into(weatherIconImageView)

        val dateFormat = SimpleDateFormat("d MMMM", Locale.getDefault())
        val timeFormat = SimpleDateFormat("HH:mm", Locale.getDefault())

        dateTextView.text = dateFormat.format(forecast.date)

        tempTextView.text = forecast.dayTemp.toString()
        weatherDescriptionTextView.text = forecast.description
        humidityCircleDiagramView.value = forecast.humidity

        pressureTextView.text = root.context
            .getString(R.string.pressure_in_mm_hg, forecast.pressure)
        windTextView.text = root.context.getString(R.string.wind_in_mps, forecast.windSpeed)
        windDirTextView.setText(forecast.windDir.nameId)
        precipitationTextView.text = root.context
            .getString(R.string.percentage_value, forecast.precipitationProb)

        mornTempTextView.text = root.context
            .getString(R.string.value_in_degrees, forecast.morningTemp)
        dayTempTextView.text = root.context
            .getString(R.string.value_in_degrees, forecast.dayTemp)
        eveTempTextView.text = root.context
            .getString(R.string.value_in_degrees, forecast.eveningTemp)
        nightTempTextView.text = root.context
            .getString(R.string.value_in_degrees, forecast.nightTemp)

        sunriseTextView.text = timeFormat.format(forecast.sunrise)
        sunsetTextView.text = timeFormat.format(forecast.sunset)
    }

}