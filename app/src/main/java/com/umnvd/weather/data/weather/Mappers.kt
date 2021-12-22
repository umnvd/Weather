package com.umnvd.weather.data.weather.forecast

import com.umnvd.weather.data.network.ICON_BASE_URL
import com.umnvd.weather.data.weather.current.CurrentWeatherNetworkModel
import com.umnvd.weather.model.City
import com.umnvd.weather.model.CurrentWeather
import com.umnvd.weather.model.WeatherForecast
import com.umnvd.weather.model.toWindDirection
import java.util.*
import kotlin.math.roundToInt

fun CurrentWeatherNetworkModel.toCurrentWeather(city: City): CurrentWeather {
    return CurrentWeather(
        updateTime = Date(System.currentTimeMillis()),
        city = city,
        temp = this.temp.temp.roundToInt()
    )
}

fun List<WeatherForecastEntity>.toWeatherForecastList(): List<WeatherForecast> {
    return map { it.toWeatherForecast() }
}

fun WeatherForecastEntity.toWeatherForecast(): WeatherForecast {
    return WeatherForecast(
        date = Date(date),
        iconUrl = iconUrl,
        description = description,
        pressure = pressure,
        humidity = humidity,
        windSpeed = windSpeed,
        windDir = windDeg.toWindDirection(),
        precipitationProb = precipitationProb,
        morningTemp = morningTemp,
        dayTemp = dayTemp,
        eveningTemp = eveningTemp,
        nightTemp = nightTemp,
        sunrise = Date(sunrise),
        sunset = Date(sunset)
    )
}

fun WeatherForecastNetworkModel.toWeatherForecastEntityList(city: City): List<WeatherForecastEntity> {
    val currentTime = System.currentTimeMillis()
    return daily.map {
        WeatherForecastEntity(
            updatedAt = currentTime,
            cityId = city.id,
            date = it.date + timezoneOffset,
            iconUrl = String.format(ICON_BASE_URL, it.weather.first().iconId),
            description = it.weather.first().description,
            humidity = it.humidity.toInt(),
            pressure = it.pressure.toInt(),
            windSpeed = it.windSpeed.roundToInt(),
            windDeg = it.windDeg.toInt(),
            precipitationProb = (it.precipitationProb * 100).roundToInt(),
            morningTemp = it.temp.morning.roundToInt(),
            dayTemp = it.temp.day.roundToInt(),
            eveningTemp = it.temp.evening.roundToInt(),
            nightTemp = it.temp.night.roundToInt(),
            sunrise = it.sunrise,
            sunset = it.sunset
        )
    }
}