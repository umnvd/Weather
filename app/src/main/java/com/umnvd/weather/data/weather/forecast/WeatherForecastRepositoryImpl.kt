package com.umnvd.weather.data.weather.forecast

import com.umnvd.weather.data.toDayWeatherForecast
import com.umnvd.weather.data.toDayWeatherForecastEntities
import com.umnvd.weather.data.util.*
import com.umnvd.weather.data.weather.WeatherApi
import com.umnvd.weather.model.City
import com.umnvd.weather.model.DayWeatherForecast
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WeatherForecastRepositoryImpl(
    private val weatherForecastDao: WeatherForecastDao,
    private val weatherApi: WeatherApi,
    private val ioDispatcher: IoDispatcher
) : WeatherForecastRepository {

    override fun getWeatherForecasts(city: City): FlowResult<List<DayWeatherForecast>> =
        networkBoundResult(
            fetchFromLocal = { weatherForecastDao.getWeatherForecast(city.id) },
            shouldFetchFromRemote = { shouldFetchFromRemote(it) },
            fetchFromRemote = { weatherApi.getWeatherForecasts(city.lat, city.lon) },
            remoteToLocalMapper = { it.toDayWeatherForecastEntities(city) },
            saveLocalData = { weatherForecastDao.insertWeatherForecast(it) }
        ).map { entitiesResult ->
            entitiesResult.map { entitiesList ->
                entitiesList.map(DayWeatherForecastEntity::toDayWeatherForecast)
            }
        }.flowOn(ioDispatcher.value)

    private fun shouldFetchFromRemote(localData: List<DayWeatherForecastEntity>): Boolean {
        return localData.isEmpty()
                || System.currentTimeMillis() > (localData.first().updatedAt + UPDATE_INTERVAL)
    }

    companion object {
        private const val UPDATE_INTERVAL = 600_000L
    }

}