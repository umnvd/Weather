package com.umnvd.weather.data

import androidx.annotation.StringRes
import com.umnvd.weather.R
import java.lang.RuntimeException

sealed class WeatherAppException(
    @StringRes val messageId: Int
) : RuntimeException()

class StorageException: WeatherAppException(R.string.storage_error)

class InternetConnectionException: WeatherAppException(R.string.internet_connection_error)

class WeatherApiException: WeatherAppException(R.string.weather_api_error)

class NoCurrentCityException: WeatherAppException(R.string.current_city_error)