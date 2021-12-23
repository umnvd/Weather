package com.umnvd.weather.data.weather.forecast

import com.umnvd.weather.data.toWeatherForecast
import com.umnvd.weather.data.toWeatherForecastEntities
import com.umnvd.weather.data.util.*
import com.umnvd.weather.data.weather.WeatherApi
import com.umnvd.weather.model.City
import com.umnvd.weather.model.WeatherForecast
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class WeatherForecastRepositoryImpl(
    private val weatherForecastDao: WeatherForecastDao,
    private val weatherApi: WeatherApi,
    private val ioDispatcher: IoDispatcher
) {

    fun getWeatherForecast(city: City): FlowResult<List<WeatherForecast>> = networkBoundResult(
        fetchFromLocal = { weatherForecastDao.getWeatherForecast(city.id) },
        shouldFetchFromRemote = { shouldFetchFromRemote(it) },
        fetchFromRemote = { weatherApi.getWeatherForecast(city.lat, city.lon) },
        remoteToLocalMapper = { it.toWeatherForecastEntities(city) },
        saveLocalData = { weatherForecastDao.insertWeatherForecast(it) }
    ).map { entitiesResult ->
        entitiesResult.map { entitiesList ->
            entitiesList.map(WeatherForecastEntity::toWeatherForecast)
        }
    }.flowOn(ioDispatcher.value)

    private fun shouldFetchFromRemote(localData: List<WeatherForecastEntity>): Boolean {
        return localData.isEmpty() || System.currentTimeMillis() > (localData.first().updatedAt + UPDATE_INTERVAL)
    }

    companion object {
        private const val UPDATE_INTERVAL = 600_000L
    }

}