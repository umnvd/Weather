package com.umnvd.weather.data.cities.cities

import android.content.Context
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.google.gson.stream.JsonReader
import com.umnvd.weather.data.AppDatabase
import kotlinx.coroutines.*
import java.io.BufferedReader

class CitiesPrepopulator {

    fun prepopulate(appDatabase: AppDatabase, context: Context) {
        CoroutineScope(Job()).launch(Dispatchers.IO) {
            val ids = context.assets
                .open(CITIES_DATA_FILE_NAME)
                .bufferedReader()
                .use { withContext(Dispatchers.Default) { readJsonArray<JsonCityData>(it) } }
                .shuffled()
                .take(CITIES_COUNT)
                .mapIndexed { index, jsonCity ->
                    CityDataEntity (
                        id = jsonCity.id,
                        lat = jsonCity.lat.toFloat(),
                        lon = jsonCity.lon.toFloat(),
                        position = index
                    )
                }
                .let { appDatabase.citiesUtilDao.insertCitiesData(it) }

            context.assets
                .open(CITIES_NAMES_FILE_NAME)
                .bufferedReader()
                .use { withContext(Dispatchers.Default) { readJsonArray<JsonCityName>(it)} }
                .filter { it.id in ids }
                .map {
                    CityNameEntity(
                        id = it.id,
                        language = it.language,
                        name = it.name
                    )
                }.let { appDatabase.citiesUtilDao.insertCitiesNames(it)  }
        }
    }

    private inline fun <reified T> readJsonArray(bufferedReader: BufferedReader): List<T> {
        val result = mutableListOf<T>()
        val gson = Gson()
        val type = TypeToken.get(T::class.java).type

        JsonReader(bufferedReader).use { jsonReader ->
            jsonReader.beginArray()
            while (jsonReader.hasNext()) {
                result.add(gson.fromJson(jsonReader, type))
            }
            jsonReader.endArray()
        }
        return result
    }

    companion object {

        private const val CITIES_DATA_FILE_NAME = "ru_city_data_list.json"
        private const val CITIES_NAMES_FILE_NAME = "ru_city_name_list.json"
        private const val CITIES_COUNT = 33

    }

}

data class JsonCityData(
    val id: Long,
    val lon: Double,
    val lat: Double
)

data class JsonCityName(
    val id: Long,
    val language: String,
    val name: String
)