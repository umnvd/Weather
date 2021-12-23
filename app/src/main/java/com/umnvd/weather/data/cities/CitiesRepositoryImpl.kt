package com.umnvd.weather.data.cities

import androidx.datastore.core.DataStore
import com.umnvd.weather.data.toCitiesListItem
import com.umnvd.weather.data.toCity
import com.umnvd.weather.model.CitiesListItem
import com.umnvd.weather.model.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext

class CitiesRepositoryImpl(
    private val currentCityDataStore: DataStore<Long>,
    private val citiesDao: CitiesDao,
    private val ioDispatcher: CoroutineDispatcher
) : CitiesRepository {

    override fun getCities(): Flow<List<CitiesListItem>> = combine(
        citiesDao.getCities(),
        currentCityDataStore.data
    ) { cityEntities: List<CitiesListItemTuple>, currentCityId: Long ->
        cityEntities.map { it.toCitiesListItem(currentCityId) }
    }.flowOn(ioDispatcher)

    override suspend fun moveCity(fromPosition: Int, toPosition: Int) = withContext(ioDispatcher) {
        return@withContext citiesDao.updatePositions(fromPosition, toPosition)
    }

    override suspend fun changeCurrentCity(newId: Long) = withContext(ioDispatcher) {
        return@withContext currentCityDataStore.updateData {
            if (it == newId) {
                CurrentCityDataStore.UNIDENTIFIED_CITY_ID
            } else {
                newId
            }
        }
    }

    override fun getCity(id: Long): Flow<City> {
        return currentCityDataStore.data
            .map { citiesDao.getCity(id).toCity(it) }
            .flowOn(ioDispatcher)
    }

    override suspend fun getCurrentCity(): City = withContext(ioDispatcher) {
        val currentCityId = currentCityDataStore.data.last()
        return@withContext citiesDao.getCity(currentCityId).toCity(currentCityId)
    }
}