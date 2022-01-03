package com.umnvd.weather.data

import android.content.Context
import android.util.JsonReader
import androidx.room.Database
import androidx.room.RoomDatabase
import com.umnvd.weather.data.cities.cities.CitiesDao
import com.umnvd.weather.data.cities.cities.CityEntity
import com.umnvd.weather.data.cities.utils.JsonCitiesService
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastDao
import com.umnvd.weather.data.weather.weather_forecast.DayWeatherForecastEntity
import kotlinx.coroutines.*

@Database(
    version = 1,
    entities = [
        CityEntity::class,
        DayWeatherForecastEntity::class
    ],
    exportSchema = false
)
abstract class AppDatabase: RoomDatabase() {

    abstract val citiesDao: CitiesDao

    abstract val weatherForecastDao: WeatherForecastDao

    companion object {

        fun onCreate(appDatabase: AppDatabase, context: Context) {
            CoroutineScope(Job()).launch(Dispatchers.IO) {
                context.assets
                    .open(CITIES_FILE_NAME)
                    .bufferedReader()
                    .use {
                        withContext(Dispatchers.Default) {
                            JsonCitiesService().readJsonCities(JsonReader(it))
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
                        appDatabase
                            .citiesDao
                            .insertCities(it)
                    }
            }
        }

        private const val CITIES_FILE_NAME = "city_list.json"
        private const val COUNTRY_FILTER = "RU"
        private const val CITIES_COUNT = 33

    }

}