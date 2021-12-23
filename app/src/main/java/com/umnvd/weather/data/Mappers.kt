package com.umnvd.weather.data

import com.umnvd.weather.data.cities.CitiesListItemTuple
import com.umnvd.weather.data.cities.CityEntity
import com.umnvd.weather.data.network.ICON_BASE_URL
import com.umnvd.weather.data.weather.current.CurrentWeatherNetworkModel
import com.umnvd.weather.data.weather.forecast.WeatherForecastEntity
import com.umnvd.weather.data.weather.forecast.WeatherForecastNetworkModel
import com.umnvd.weather.model.*
import java.util.*
import kotlin.math.roundToInt

 fun CityEntity.toCity(currentCityId: Long): City {
    return City(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        isCurrent = this.id == currentCityId
    )
}

fun CitiesListItemTuple.toCitiesListItem(currentCityId: Long): CitiesListItem {
    return CitiesListItem(
        id = this.id,
        name = this.name,
        isCurrent = this.id == currentCityId
    )
}

fun WeatherForecastNetworkModel.toWeatherForecastEntities(city: City): List<WeatherForecastEntity> {
    val currentTime = System.currentTimeMillis()
    return daily.mapIndexed { index, daily ->
        WeatherForecastEntity(
            updatedAt = currentTime,
            cityId = city.id,
            dayId = index.toLong() + 1,
            date = daily.date + timezoneOffset,
            iconUrl = String.format(ICON_BASE_URL, daily.weather.first().iconId),
            description = daily.weather.first().description,
            humidity = daily.humidity.toInt(),
            pressure = daily.pressure.toInt(),
            windSpeed = daily.windSpeed.roundToInt(),
            windDeg = daily.windDeg.toInt(),
            precipitationProb = (daily.precipitationProb * 100).roundToInt(),
            morningTemp = daily.temp.morning.roundToInt(),
            dayTemp = daily.temp.day.roundToInt(),
            eveningTemp = daily.temp.evening.roundToInt(),
            nightTemp = daily.temp.night.roundToInt(),
            sunrise = daily.sunrise,
            sunset = daily.sunset
        )
    }
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

fun CurrentWeatherNetworkModel.toCurrentWeather(city: City): CurrentWeather {
    return CurrentWeather(
        updateTime = Date(System.currentTimeMillis()),
        city = city,
        temp = this.temp.temp.roundToInt()
    )
}