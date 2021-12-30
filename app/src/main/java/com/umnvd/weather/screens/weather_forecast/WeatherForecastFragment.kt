package com.umnvd.weather.screens.weather_forecast

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.umnvd.weather.R
import com.umnvd.weather.databinding.FragmentWeatherForecastBinding

class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    private lateinit var binding: FragmentWeatherForecastBinding

    private val args: WeatherForecastFragmentArgs by navArgs()

    private val viewModel: WeatherForecastViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherForecastBinding.bind(view)



    }



    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }
}