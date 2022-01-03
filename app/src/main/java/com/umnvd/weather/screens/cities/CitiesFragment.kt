package com.umnvd.weather.screens.cities

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umnvd.weather.R
import com.umnvd.weather.appComponent
import com.umnvd.weather.databinding.FragmentCitiesBinding
import com.umnvd.weather.models.CitiesListItem
import com.umnvd.weather.screens.SavedStateViewModelsFactory
import com.umnvd.weather.screens.cities.adapters.CitiesAdapter
import com.umnvd.weather.screens.cities.adapters.CityItemTouchCallback
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

        val layoutManager = LinearLayoutManager(
            requireContext(), RecyclerView.VERTICAL, false
        )
        val adapter = CitiesAdapter(object : CitiesAdapter.Listener {
            override fun onCityIsCurrentClick(cityItem: CitiesListItem) {
                viewModel.changeCurrentCity(cityItem)
            }

            override fun onCityItemClick(cityItem: CitiesListItem) {
                showWeatherForecast(cityItem)
            }

            override fun onCityItemMove(fromPosition: Int, toPosition: Int) {
                viewModel.moveCity(fromPosition, toPosition)
            }
        })
        val touchHelper = ItemTouchHelper(CityItemTouchCallback(adapter))
        val dividerDecoration = DividerItemDecoration(requireContext(), layoutManager.orientation)

        with(binding.citiesRecycler) {
            this.layoutManager = layoutManager
            this.adapter = adapter
            touchHelper.attachToRecyclerView(this)
            addItemDecoration(dividerDecoration)
            setHasFixedSize(true)
        }

        viewModel.liveCities.observe(viewLifecycleOwner, adapter::setCities)

    }

    private fun showWeatherForecast(cityItem: CitiesListItem) {
        val destination = CitiesFragmentDirections
            .actionCitiesFragmentToWeatherForecastFragment(cityItem.id, cityItem.name)

        findNavController().navigate(destination)
    }

}