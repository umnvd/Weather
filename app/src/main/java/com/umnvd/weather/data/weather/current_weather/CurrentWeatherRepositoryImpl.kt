package com.umnvd.weather.data.weather.current_weather

import com.umnvd.weather.data.utils.FinalResult
import com.umnvd.weather.data.utils.takeResult
import com.umnvd.weather.data.weather.WeatherApiService
import com.umnvd.weather.data.toCurrentWeather
import com.umnvd.weather.di.IO
import com.umnvd.weather.models.City
import com.umnvd.weather.models.CurrentWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val weatherApiService: WeatherApiService,
    @IO private val ioDispatcher: CoroutineDispatcher
): CurrentWeatherRepository {

    override suspend fun getCurrentWeather(city: City): FinalResult<CurrentWeather> =
        withContext(ioDispatcher) {
            return@withContext weatherApiService.getCurrentWeather(city.id)
                .takeResult()
                .map { it.toCurrentWeather(city) }
        }

}