package com.umnvd.weather.data.weather.current

import com.umnvd.weather.data.util.FinalResult
import com.umnvd.weather.data.util.IoDispatcher
import com.umnvd.weather.data.util.toFinalResult
import com.umnvd.weather.data.weather.WeatherApi
import com.umnvd.weather.data.toCurrentWeather
import com.umnvd.weather.data.util.ErrorResult
import com.umnvd.weather.model.City
import com.umnvd.weather.model.CurrentWeather
import kotlinx.coroutines.withContext

class CurrentWeatherRepositoryImpl(
    private val weatherApi: WeatherApi,
    private val ioDispatcher: IoDispatcher
): CurrentWeatherRepository {

    override suspend fun getCurrentWeather(city: City): FinalResult<CurrentWeather> =
        withContext(ioDispatcher.value) {
            return@withContext weatherApi.getCurrentWeather(city.id)
                .toFinalResult()
                .map { it.toCurrentWeather(city) }
        }

}