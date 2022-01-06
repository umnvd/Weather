package com.umnvd.weather.screens.cities

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.umnvd.weather.data.cities.CitiesRepository
import com.umnvd.weather.models.CitiesListItem
import com.umnvd.weather.utils.share
import kotlinx.coroutines.launch
import javax.inject.Inject

class CitiesViewModel @Inject constructor(
    private val citiesRepository: CitiesRepository
): ViewModel() {

    private val _liveCities: MutableLiveData<List<CitiesListItem>> = MutableLiveData()
    val liveCities = _liveCities.share()

    init {
        viewModelScope.launch {
            citiesRepository.getCities().collect { cities ->
                _liveCities.postValue(cities)
            }
        }
    }

    fun changeCurrentCity(cityItem: CitiesListItem) {
        viewModelScope.launch {
            citiesRepository.changeCurrentCity(cityItem.id)
        }
    }

    fun moveCity(fromPosition: Int, toPosition: Int) {
        viewModelScope.launch {
            citiesRepository.moveCity(fromPosition, toPosition)
        }
    }

}