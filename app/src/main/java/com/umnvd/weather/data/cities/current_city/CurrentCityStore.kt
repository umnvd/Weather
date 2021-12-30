package com.umnvd.weather.data.cities.current_city

import kotlinx.coroutines.flow.Flow

interface CurrentCityStore {

    fun getCurrentCityId(): Flow<Long>

    suspend fun setCurrentCityId(id: Long)

    companion object {

        const val NO_CURRENT_CITY_ID = -1L

    }

}