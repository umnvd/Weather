package com.umnvd.weather.data

import com.umnvd.weather.data.cities.cities.CityDbView
import com.umnvd.weather.data.weather.ICON_BASE_URL
import com.umnvd.weather.data.weather.current_weather.CurrentWeatherNetworkModel
import com.umnvd.weather.data.weather.weather_forecast.DayWeatherForecastEntity
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastNetworkModel
import com.umnvd.weather.models.*
import java.util.*
import kotlin.math.roundToInt

fun CityDbView.toCity(): City {
    return City(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon,
    )
}

fun CityDbView.toCitiesListItem(isCurrent: Boolean): CitiesListItem {
    return CitiesListItem(
        id = this.id,
        name = this.name,
        isCurrent = isCurrent
    )
}

fun WeatherForecastNetworkModel.toDayWeatherForecastEntities(cityId: Long): List<DayWeatherForecastEntity> {
    val currentTime = System.currentTimeMillis()
    return daily.mapIndexed { index, daily ->
        DayWeatherForecastEntity(
            updatedAt = currentTime,
            cityId = cityId,
            dayId = index.toLong() + 1,
            date = (daily.date + timezoneOffset) * 1000,
            iconUrl = String.format(ICON_BASE_URL, daily.weather[0].iconId),
            description = daily.weather.first().description.replaceFirstChar {
                if (it.isLowerCase()) it.titlecase(Locale.getDefault())
                else it.toString()
            },
            humidity = daily.humidity.toInt(),
            pressure = daily.pressure.toInt(),
            windSpeed = daily.windSpeed.roundToInt(),
            windDeg = daily.windDeg.toInt(),
            precipitationProb = (daily.precipitationProb * 100).roundToInt(),
            morningTemp = daily.temp.morning.roundToInt(),
            dayTemp = daily.temp.day.roundToInt(),
            eveningTemp = daily.temp.evening.roundToInt(),
            nightTemp = daily.temp.night.roundToInt(),
            sunrise = (daily.sunrise + timezoneOffset) * 1000,
            sunset = (daily.sunset + timezoneOffset) * 1000,
        )
    }
}

fun DayWeatherForecastEntity.toDayWeatherForecast(): DayWeatherForecast {
    return DayWeatherForecast(
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

fun CurrentWeatherNetworkModel.toCurrentWeather(cityName: String): CurrentWeather {
    return CurrentWeather(
        cityName = cityName,
        iconUrl = String.format(ICON_BASE_URL, weather[0].iconId),
        temp = temp.temp.roundToInt()
    )
}