package com.umnvd.weather.data

import com.umnvd.weather.data.cities.cities.CitiesListItemTuple
import com.umnvd.weather.data.cities.cities.CityEntity
import com.umnvd.weather.data.weather.ICON_BASE_URL
import com.umnvd.weather.data.weather.current_weather.CurrentWeatherNetworkModel
import com.umnvd.weather.data.weather.weather_forecast.DayWeatherForecastEntity
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastNetworkModel
import com.umnvd.weather.models.*
import java.util.*
import kotlin.math.roundToInt

 fun CityEntity.toCity(isCurrent: Boolean): City {
    return City(
        id = this.id,
        name = this.name,
        lat = this.lat,
        lon = this.lon,
        isCurrent = isCurrent
    )
}

fun CitiesListItemTuple.toCitiesListItem(isCurrent: Boolean): CitiesListItem {
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

fun CurrentWeatherNetworkModel.toCurrentWeather(city: City): CurrentWeather {
    return CurrentWeather(
        updateTime = Date(System.currentTimeMillis()),
        city = city,
        temp = this.temp.temp.roundToInt()
    )
}