package com.umnvd.weather.screens.weather_forecast

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.weather.data.cities.CitiesRepository
import com.umnvd.weather.data.utils.ErrorResult
import com.umnvd.weather.data.utils.PendingResult
import com.umnvd.weather.data.utils.Result
import com.umnvd.weather.data.weather.weather_forecast.WeatherForecastRepository
import com.umnvd.weather.models.City
import com.umnvd.weather.models.DayWeatherForecast
import com.umnvd.weather.screens.AssistedViewModelFactory
import com.umnvd.weather.utils.Event
import com.umnvd.weather.utils.MutableLiveEvent
import com.umnvd.weather.utils.publishEvent
import com.umnvd.weather.utils.share
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.last
import kotlinx.coroutines.launch
import javax.inject.Inject

class WeatherForecastViewModel @AssistedInject constructor(
    @Assisted handle: SavedStateHandle,
    private val citiesRepository: CitiesRepository,
    private val weatherForecastRepository: WeatherForecastRepository
): ViewModel() {

    private val args = WeatherForecastFragmentArgs.fromSavedStateHandle(handle)

    private val _city = MutableLiveData<City>()
    val liveCity= _city.share()

    private val _forecast = MutableLiveData<List<DayWeatherForecast>>()
    val liveForecast = _forecast.share()

    private val _errorMessage = MutableLiveEvent<String>()
    val errorMessage = _errorMessage.share()

    private val _showProgress = MutableLiveEvent(Event(false))
    val showProgress = _showProgress.share()

    init {
        viewModelScope.launch {
            weatherForecastRepository.getWeatherForecast(
                citiesRepository.getCity(args.cityId).last()
            ).collect {
                processResult(it)
            }

            citiesRepository.getCity(args.cityId).collect {
                _city.postValue(it)
            }
        }
    }

    fun changeCurrentCity() {
        viewModelScope.launch {
            citiesRepository.changeCurrentCity(args.cityId)
        }
    }

    fun getWeatherForecastText(): String {
        val city = _city.value ?: return ""
        val forecast = _forecast.value ?: return ""
        return "CityName "
    }

    private fun processResult(result: Result<List<DayWeatherForecast>>) {
        if (result is PendingResult) {
            _showProgress.publishEvent(true)
        } else {
            _showProgress.publishEvent(false)
        }

        if (result is ErrorResult) {
            _errorMessage.publishEvent(result.message)
        }

        val resultData = result.data ?: return
        _forecast.postValue(resultData)
    }

    @AssistedFactory
    interface Factory: AssistedViewModelFactory<WeatherForecastViewModel> {
        override fun create(handle: SavedStateHandle): WeatherForecastViewModel
    }

}