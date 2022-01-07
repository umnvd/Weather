package com.umnvd.weather.data.cities

import android.database.sqlite.SQLiteException
import com.umnvd.weather.data.NoCurrentCityException
import com.umnvd.weather.data.StorageException
import com.umnvd.weather.data.cities.cities.CitiesDao
import com.umnvd.weather.data.cities.cities.CitiesListItemTuple
import com.umnvd.weather.data.cities.current_city.CurrentCityStore
import com.umnvd.weather.data.cities.current_city.CurrentCityStore.Companion.NO_CURRENT_CITY_ID
import com.umnvd.weather.data.toCitiesListItem
import com.umnvd.weather.data.toCity
import com.umnvd.weather.di.InputOutput
import com.umnvd.weather.models.CitiesListItem
import com.umnvd.weather.models.City
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CitiesRepositoryImpl @Inject constructor(
    private val currentCityStore: CurrentCityStore,
    private val citiesDao: CitiesDao,
    @InputOutput private val ioDispatcher: CoroutineDispatcher
) : CitiesRepository {

    override fun getCities(): Flow<List<CitiesListItem>> = combine(
        citiesDao.getCities(),
        currentCityStore.getCurrentCityId()
    ) { cityEntities: List<CitiesListItemTuple>, currentCityId: Long ->
        cityEntities.map { cityTuple ->
            cityTuple.toCitiesListItem(cityTuple.id == currentCityId)
        }
    }.flowOn(ioDispatcher)

    override suspend fun moveCity(fromPosition: Int, toPosition: Int) =
        withContext(ioDispatcher) {
            citiesDao.updateCitiesPositions(fromPosition, toPosition)
        }

    override fun isCityCurrent(cityId: Long): Flow<Boolean> {
        return currentCityStore.getCurrentCityId()
            .map { it == cityId }
            .flowOn(ioDispatcher)
    }

    override suspend fun changeCurrentCity(id: Long) = withContext(ioDispatcher) {
        val currentId = currentCityStore.getCurrentCityId().first()
        val newId = if (currentId == id) {
            NO_CURRENT_CITY_ID
        } else {
            id
        }
        currentCityStore.setCurrentCityId(newId)
    }

    override suspend fun getCity(id: Long): City = withContext(ioDispatcher) {
        return@withContext citiesDao.getCityById(id).toCity()
    }

    override suspend fun getCurrentCity(): City = withContext(ioDispatcher) {
        val currentCityId = currentCityStore.getCurrentCityId().first()
        return@withContext try {
            citiesDao.getCityById(currentCityId).toCity()
        } catch (e: SQLiteException) {
            throw NoCurrentCityException().apply { initCause(e) }
        }
    }
}