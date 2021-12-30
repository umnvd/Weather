package com.umnvd.weather.data.cities.utils

import android.content.Context
import android.util.JsonReader
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.umnvd.weather.data.AppDatabase
import com.umnvd.weather.data.cities.cities.CityEntity
import kotlinx.coroutines.*

class CitiesPrePopulationCallback(
    private val context: Context,
    private val database: AppDatabase,
): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        CoroutineScope(Job()).launch(Dispatchers.IO) {
            context.assets
                .open(CITIES_FILE_NAME)
                .bufferedReader()
                .use {
                    withContext(Dispatchers.Default) {
                        readJsonCitiesArray(JsonReader(it))
                    }
                }
                .filter { it.country == COUNTRY_FILTER }
                .shuffled()
                .take(CITIES_COUNT)
                .mapIndexed { index, jsonCity ->
                    CityEntity(
                        id = jsonCity.id,
                        name = jsonCity.name,
                        lat = jsonCity.coord.lat.toFloat(),
                        lon = jsonCity.coord.lon.toFloat(),
                        position = index
                    )
                }
                .let {
                    database
                        .citiesDao
                        .insertCities(it)
                }
        }
    }

    companion object {
        private const val CITIES_FILE_NAME = "city_list.json"
        private const val COUNTRY_FILTER = "RU"
        private const val CITIES_COUNT = 33
    }

    private fun readJsonCitiesArray(reader: JsonReader): List<JsonCity> {
        val jsonCities = mutableListOf<JsonCity>()
        reader.beginArray()
        while (reader.hasNext()) {
            jsonCities.add(readJsonCity(reader))
        }
        reader.endArray()
        return jsonCities.toList()
    }

    private fun readJsonCity(reader: JsonReader): JsonCity {
        var id = -1L
        var name = ""
        var country = ""
        var coord = JsonCoord(1.0, 1.0)

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "id" -> id = reader.nextLong()
                "name" -> name = reader.nextString()
                "state" -> reader.skipValue()
                "country" -> country = reader.nextString()
                "coord" -> coord = readJsonCoord(reader)
            }
        }
        reader.endObject()

        return JsonCity(id, name, country, coord)
    }

    private fun readJsonCoord(reader: JsonReader): JsonCoord {
        var lon = 1.0
        var lat = 1.0

        reader.beginObject()
        while (reader.hasNext()) {
            when (reader.nextName()) {
                "lon" -> lon = reader.nextDouble()
                "lat" -> lat = reader.nextDouble()
            }
        }
        reader.endObject()

        return JsonCoord(lon, lat)
    }

}

data class JsonCity(
    val id: Long,
    val name: String,
    val country: String,
    val coord: JsonCoord,
)

data class JsonCoord(
    val lon: Double,
    val lat: Double
)