package com.umnvd.weather.data.cities.current_city

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.umnvd.weather.data.cities.current_city.CurrentCityStore.Companion.NO_CURRENT_CITY_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

private const val STORE_NAME = "current_city_data_store"
private val Context.currentCityDataStore: DataStore<Preferences>
        by preferencesDataStore(name = STORE_NAME)

class CurrentCityStoreImpl @Inject constructor(
    context: Context
) : CurrentCityStore {

    private val dataStore = context.currentCityDataStore

    override fun getCurrentCityId(): Flow<Long> = dataStore.data
        .map {
            it[ID_KEY] ?: NO_CURRENT_CITY_ID
        }

    override suspend fun setCurrentCityId(id: Long) {
        dataStore.edit {
            it[ID_KEY] = id
        }
    }

    companion object {

        private val ID_KEY = longPreferencesKey("current_city_id")

    }

}