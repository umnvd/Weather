package com.umnvd.weather.data

import java.lang.RuntimeException

sealed class WeatherAppException : RuntimeException()

class StorageException: WeatherAppException()

class InternetConnectionException: WeatherAppException()

class NetworkException: WeatherAppException()

class NoCurrentCityException: WeatherAppException()