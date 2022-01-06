package com.umnvd.weather.data.weather.weather_forecast

import com.umnvd.weather.data.toDayWeatherForecast
import com.umnvd.weather.data.toDayWeatherForecastEntities
import com.umnvd.weather.data.utils.Result
import com.umnvd.weather.data.utils.networkBoundResult
import com.umnvd.weather.data.weather.WeatherApiService
import com.umnvd.weather.di.InputOutput
import com.umnvd.weather.models.City
import com.umnvd.weather.models.DayWeatherForecast
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class WeatherForecastRepositoryImpl @Inject constructor(
    private val weatherForecastDao: WeatherForecastDao,
    private val weatherApiService: WeatherApiService,
    @InputOutput private val ioDispatcher: CoroutineDispatcher
) : WeatherForecastRepository {

    override fun getWeatherForecast(city: City): Flow<Result<List<DayWeatherForecast>>> =
        networkBoundResult(
            fetchFromLocal = { weatherForecastDao.getWeatherForecast(city.id) },
            shouldFetchFromRemote = { shouldFetchFromRemote(it) },
            fetchFromRemote = { weatherApiService.getWeatherForecasts(city.lat, city.lon) },
            saveRemoteData = {
                weatherForecastDao.insertWeatherForecast(it.toDayWeatherForecastEntities(city.id))
            }
        ).map { entitiesResult ->
            entitiesResult.map { entitiesList ->
                entitiesList.map(DayWeatherForecastEntity::toDayWeatherForecast)
            }
        }.flowOn(ioDispatcher)

    private fun shouldFetchFromRemote(localData: List<DayWeatherForecastEntity>): Boolean {
        return localData.isEmpty()
                || System.currentTimeMillis() > (localData.first().updatedAt + UPDATE_INTERVAL)
    }

    companion object {
        private const val UPDATE_INTERVAL = 600_000L
    }

}