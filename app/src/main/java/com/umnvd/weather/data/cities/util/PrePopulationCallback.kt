package com.umnvd.weather.data.cities.util

import android.content.Context
import android.util.Log
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.umnvd.weather.data.AppDatabase
import com.umnvd.weather.data.cities.CityEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class PrePopulationCallback(
    private val context: Context,
): RoomDatabase.Callback() {

    override fun onCreate(db: SupportSQLiteDatabase) {
        super.onCreate(db)
        Log.d("PREPOPULATE", "DB pre-population")
        CoroutineScope(Job()).launch(Dispatchers.IO) {
            CitiesJsonParser(context).getCitiesFromJson()
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
                    AppDatabase
                        .getInstance(context)
                        .citiesDao
                        .insertCities(it)
                }
        }
    }

    companion object {
        private const val COUNTRY_FILTER = "RU"
        private const val CITIES_COUNT = 33
    }

}