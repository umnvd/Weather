package com.umnvd.weather.screens.cities

import android.os.Bundle
import android.view.View
import androidx.navigation.NavOptions
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.umnvd.weather.R
import com.umnvd.weather.databinding.FragmentCitiesBinding
import com.umnvd.weather.di.AppComponent
import com.umnvd.weather.models.CitiesListItem
import com.umnvd.weather.screens.BaseFragment
import com.umnvd.weather.screens.cities.adapters.CitiesAdapter
import com.umnvd.weather.screens.cities.adapters.CityItemTouchCallback
import com.umnvd.weather.screens.savedStateViewModels

class CitiesFragment: BaseFragment(R.layout.fragment_cities) {

    private val viewModel: CitiesViewModel by savedStateViewModels()

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val binding = FragmentCitiesBinding.bind(view)

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

        with(binding.citiesRecycler) {
            this.layoutManager = LinearLayoutManager(
                requireContext(), RecyclerView.VERTICAL, false
            )
            this.adapter = adapter
            ItemTouchHelper(CityItemTouchCallback(adapter)).attachToRecyclerView(this)
            addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.VERTICAL))
            setHasFixedSize(true)
        }

        viewModel.liveCities.observe(viewLifecycleOwner, adapter::setCities)
    }

    private fun showWeatherForecast(cityItem: CitiesListItem) {
        val destination = CitiesFragmentDirections
            .actionCitiesFragmentToWeatherForecastFragment(cityItem.id, cityItem.name)

        findNavController().navigate(
            destination,
            NavOptions.Builder()
                .setEnterAnim(R.anim.enter)
                .setPopEnterAnim(R.anim.pop_enter)
                .setExitAnim(R.anim.exit)
                .setPopExitAnim(R.anim.pop_exit)
                .build()
        )
    }

}