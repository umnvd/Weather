package com.umnvd.weather.data.cities

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private const val CURRENT_CITY_PREFERENCES_NAME = "current_city"
private val Context.currentCityDataStore: DataStore<Preferences>
        by preferencesDataStore(name = CURRENT_CITY_PREFERENCES_NAME)

data class CurrentCityPreferences(
    val id: Long
)

class CurrentCityDataStore(context: Context) {

    private val dataStore = context.currentCityDataStore

    val currentCityIdFlow: Flow<Long> = dataStore.data
        .map {
            it[CURRENT_CITY_ID_KEY] ?: -1
        }

    suspend fun editCurrentCityId(cityId: Long) {
        dataStore.edit {
            it[CURRENT_CITY_ID_KEY] = cityId
        }
    }

    companion object {
        const val UNIDENTIFIED_CITY_ID = -1L
        private val CURRENT_CITY_ID_KEY = longPreferencesKey("current_city_id")
    }

}