package com.umnvd.weather.screens.weather_forecast

import android.content.Context
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.tabs.TabLayoutMediator
import com.umnvd.weather.R
import com.umnvd.weather.appComponent
import com.umnvd.weather.databinding.FragmentWeatherForecastBinding
import com.umnvd.weather.screens.SavedStateViewModelsFactory
import com.umnvd.weather.screens.weather_forecast.adapters.WeatherForecastAdapter
import javax.inject.Inject

class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    @Inject
    lateinit var factory: SavedStateViewModelsFactory

    private lateinit var binding: FragmentWeatherForecastBinding

    private val viewModel: WeatherForecastViewModel by viewModels {
        factory.create(this, arguments)
    }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherForecastBinding.bind(view)

        val adapter = WeatherForecastAdapter()
        binding.forecastPager.adapter = adapter

        TabLayoutMediator(
            binding.forecastTabs,
            binding.forecastPager
        ) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        viewModel.liveForecast.observe(this) {
            adapter.setWeatherForecast(it)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
    }
}