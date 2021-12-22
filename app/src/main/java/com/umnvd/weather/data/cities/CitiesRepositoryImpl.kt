package com.umnvd.weather.data.cities

import androidx.datastore.core.DataStore
import com.umnvd.weather.data.CitiesRepository
import com.umnvd.weather.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class CitiesRepositoryImpl(
    private val currentCityDataStore: DataStore<Long>,
    private val citiesDao: CitiesDao,
    private val ioDispatcher: CoroutineDispatcher
) : CitiesRepository {

    override fun getCities(): Flow<List<City>> = combine(
        citiesDao.getCities(),
        currentCityDataStore.data
    ) { cityEntities: List<CityEntity>, currentCityId: Long ->
        return@combine cityEntities
            .map { mapToCity(it, currentCityId) }
    }
        .flowOn(ioDispatcher)

    override suspend fun moveCity(fromPosition: Int, toPosition: Int) = withContext(ioDispatcher) {
        citiesDao.updatePositions(fromPosition, toPosition)
    }

    override suspend fun changeCurrentCity(newId: Long) = withContext(ioDispatcher) {
        currentCityDataStore.updateData {
            return@updateData if (it == newId) {
                CurrentCityDataStore.UNIDENTIFIED_CITY_ID
            } else {
                newId
            }
        }
    }

    override fun getCity(id: Long): Flow<City> = currentCityDataStore.data
        .map {
            val cityEntity = citiesDao.getCity(id)
            mapToCity(cityEntity, it)
        }
        .flowOn(ioDispatcher)

    override suspend fun getCurrentCity(): City = withContext(ioDispatcher) {
        val currentCityId = currentCityDataStore.data.last()
        val currentCityEntity = citiesDao.getCity(currentCityId)
        return@withContext mapToCity(currentCityEntity, currentCityId)
    }

    private fun mapToCity(cityEntity: CityEntity, currentCityId: Long): City {
        return City(
            id = cityEntity.id,
            name = cityEntity.name,
            lat = cityEntity.lat,
            lon = cityEntity.lon,
            isCurrent = cityEntity.id == currentCityId
        )
    }

}