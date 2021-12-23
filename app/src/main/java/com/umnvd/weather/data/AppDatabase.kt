package com.umnvd.weather.data

import android.content.Context
import android.util.Log
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.umnvd.weather.data.cities.CitiesDao
import com.umnvd.weather.data.cities.CityEntity
import com.umnvd.weather.data.cities.util.PrePopulationCallback
import com.umnvd.weather.data.weather.forecast.WeatherForecastDao
import com.umnvd.weather.data.weather.forecast.DayWeatherForecastEntity

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

        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(applicationContext: Context): AppDatabase {
            Log.d("PREPOPULATE", "getInstance")
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(applicationContext).also { instance = it }
            }
        }

        private fun buildDatabase(applicationContext: Context): AppDatabase {
            Log.d("PREPOPULATE", "buildDatabase")
            return Room.databaseBuilder(
                applicationContext,
                AppDatabase::class.java,
                "weather.db"
            )
                .addCallback(PrePopulationCallback(applicationContext))
                .build()
        }
    }

}