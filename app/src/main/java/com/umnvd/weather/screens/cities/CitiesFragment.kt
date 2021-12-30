package com.umnvd.weather.screens.cities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.umnvd.weather.R
import com.umnvd.weather.appComponent
import com.umnvd.weather.databinding.FragmentCitiesBinding
import com.umnvd.weather.screens.SavedStateViewModelsFactory
import javax.inject.Inject

class CitiesFragment: Fragment(R.layout.fragment_cities) {

    @Inject
    lateinit var factory: SavedStateViewModelsFactory

    private val viewModel: CitiesViewModel by viewModels { factory.create(this) }

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentCitiesBinding.bind(view)



    }

    private fun testNavigate() {
        val destination = CitiesFragmentDirections
            .actionCitiesFragmentToWeatherForecastFragment(111, "Test test")

        findNavController().navigate(destination)
    }

}