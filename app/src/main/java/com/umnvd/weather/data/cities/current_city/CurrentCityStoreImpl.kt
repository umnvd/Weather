package com.umnvd.weather.data.cities.current_city

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.umnvd.weather.data.cities.current_city.CurrentCityStore.Companion.NO_CURRENT_CITY_ID
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

class CurrentCityStoreImpl @Inject constructor(
    private val context: Context
) : CurrentCityStore {

    override fun getCurrentCityId(): Flow<Long> {
        return context.dataStore.data
            .catch { exception ->
                when (exception) {
                    is IOException -> emptyPreferences()
                    else -> throw exception
                }
            }
            .map {
                it[ID_KEY] ?: NO_CURRENT_CITY_ID
            }
    }

    override suspend fun setCurrentCityId(id: Long) {
        context.dataStore.edit { it[ID_KEY] = id }
    }

    companion object {

        private const val DATA_STORE_NAME = "current_city_data_store"

        private val ID_KEY = longPreferencesKey("id_key")

        private val Context.dataStore by preferencesDataStore(
            name = DATA_STORE_NAME
        )

    }

}