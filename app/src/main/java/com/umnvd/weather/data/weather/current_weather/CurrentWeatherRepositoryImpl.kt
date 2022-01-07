package com.umnvd.weather.data.weather.current_weather

import com.umnvd.weather.data.cities.CitiesRepository
import com.umnvd.weather.data.toCurrentWeather
import com.umnvd.weather.data.utils.FinalResult
import com.umnvd.weather.data.utils.takeResult
import com.umnvd.weather.data.weather.WeatherApiService
import com.umnvd.weather.di.InputOutput
import com.umnvd.weather.models.CurrentWeather
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CurrentWeatherRepositoryImpl @Inject constructor(
    private val citiesRepository: CitiesRepository,
    private val weatherApiService: WeatherApiService,
    @InputOutput private val ioDispatcher: CoroutineDispatcher
): CurrentWeatherRepository {

    override suspend fun getCurrentWeather(): FinalResult<CurrentWeather> =
        withContext(ioDispatcher) {
            val currentCity = citiesRepository.getCurrentCity()
            return@withContext weatherApiService.getCurrentWeather(currentCity.id)
                .takeResult()
                .map { it.toCurrentWeather(currentCity.name) }
        }

}