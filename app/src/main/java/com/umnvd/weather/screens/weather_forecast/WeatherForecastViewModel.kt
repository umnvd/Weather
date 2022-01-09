package com.umnvd.weather.screens.weather_forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.weather.R
import com.umnvd.weather.data.InternetConnectionException
import com.umnvd.weather.data.NetworkException
import com.umnvd.weather.data.StorageException
import com.umnvd.weather.data.WeatherAppException
import com.umnvd.weather.data.cities.CitiesRepository
import com.umnvd.weather.data.utils.ErrorResult
import com.umnvd.weather.data.utils.PendingResult
import com.umnvd.weather.data.utils.Result
import com.umnvd.weather.data.utils.SuccessResult
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastRepository
import com.umnvd.weather.di.AssistedViewModelFactory
import com.umnvd.weather.models.DayWeatherForecast
import com.umnvd.weather.utils.*
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class WeatherForecastViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    private val citiesRepository: CitiesRepository,
    private val weatherForecastRepository: WeatherForecastRepository
) : ViewModel() {

    private val args = WeatherForecastFragmentArgs.fromSavedStateHandle(handle)

    private val _currentPage = handle.getLiveData("PAGE_KEY", 0)
    val currentPage = _currentPage.share()

    private val _isCityCurrent = MutableLiveData(false)
    val isCityCurrent = _isCityCurrent.share()

    private val _weatherForecast = MutableLiveData<List<DayWeatherForecast>>()
    val weatherForecast = _weatherForecast.share()

    private val _messageEvents = MutableLiveEvent<MessageConfig>()
    val messageEvent = _messageEvents.share()

    init {
        loadWeatherForecast()
    }

    fun changeCurrentCity() {
        viewModelScope.launch {
            citiesRepository.changeCurrentCity(args.cityId)
        }
    }

    fun getWeatherForecastText(): String? {
        val forecast = _weatherForecast.value
        if (forecast.isNullOrEmpty()) return null
        return prepareForecastText(args.cityName, forecast)
    }

    fun updateCurrentPage(page: Int) {
        if (_currentPage.value != page) {
            _currentPage.value = page
        }
    }

    private fun loadWeatherForecast() {
        viewModelScope.launch {
            val city = citiesRepository.getCity(args.cityId)
            weatherForecastRepository.getWeatherForecast(city).collect(::processResult)
            citiesRepository.isCityCurrent(args.cityId).collect(_isCityCurrent::setValue)
        }
    }

    private fun prepareForecastText(cityName: String, forecast: List<DayWeatherForecast>): String {
        val dateFormat = SimpleDateFormat("dd.MM", Locale.getDefault())
        val forecastTextBuilder = StringBuilder().appendLine(cityName)
        forecast.forEach {
            forecastTextBuilder.appendLine(
                "${dateFormat.format(it.date)} - ${it.description}, ${it.dayTemp}°C"
            )
        }
        return forecastTextBuilder.toString()
    }

    private fun processResult(result: Result<List<DayWeatherForecast>>) {
        when (result) {
            is PendingResult -> onPending()
            is ErrorResult -> onError(result.error)
            is SuccessResult -> onSuccess()
        }
        result.data?.let { _weatherForecast.value = it }
    }

    private fun onPending() {
        _messageEvents.publishEvent(
            MessageConfig(
                message = R.string.updating,
                isInfinite = true
            )
        )
    }

    private fun onError(error: WeatherAppException) {
        val messageId = when (error) {
            is StorageException -> R.string.storage_error
            is InternetConnectionException -> R.string.internet_connection_error
            is NetworkException -> R.string.network_error
            else -> R.string.error
        }

        _messageEvents.publishEvent(
            MessageConfig(
                message = messageId,
                actionConfig = ActionConfig(
                    title = R.string.try_again,
                    action = ::loadWeatherForecast
                )
            )
        )
    }

    private fun onSuccess() {
        _messageEvents.publishEvent(MessageConfig(message = R.string.updated))
    }

    @AssistedFactory
    interface Factory : AssistedViewModelFactory<WeatherForecastViewModel> {

        override fun create(handle: SavedStateHandle): WeatherForecastViewModel

    }

}