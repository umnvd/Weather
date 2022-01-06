package com.umnvd.weather.screens.weather_forecast

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.umnvd.weather.R
import com.umnvd.weather.appComponent
import com.umnvd.weather.databinding.FragmentWeatherForecastBinding
import com.umnvd.weather.screens.SavedStateViewModelsFactory
import com.umnvd.weather.screens.weather_forecast.adapters.WeatherForecastAdapter
import com.umnvd.weather.utils.MessageConfig
import com.umnvd.weather.utils.observeEvent
import javax.inject.Inject

class WeatherForecastFragment : Fragment(R.layout.fragment_weather_forecast) {

    @Inject
    lateinit var factory: SavedStateViewModelsFactory
    private val viewModel: WeatherForecastViewModel by viewModels {
        factory.create(this, arguments)
    }

    private lateinit var binding: FragmentWeatherForecastBinding

    override fun onAttach(context: Context) {
        context.appComponent.inject(this)
        super.onAttach(context)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setHasOptionsMenu(true)
        binding = FragmentWeatherForecastBinding.bind(view)

        val adapter = WeatherForecastAdapter()
        binding.forecastPager.adapter = adapter

        TabLayoutMediator(
            binding.forecastTabs,
            binding.forecastPager,
            true,
        ) { tab, position ->
            tab.text = adapter.getPageTitle(position)
        }.attach()

        viewModel.isCityCurrent.observe(this) { requireActivity().invalidateOptionsMenu() }
        viewModel.weatherForecast.observe(this, adapter::setWeatherForecast)
        viewModel.messageEvent.observeEvent(viewLifecycleOwner, ::showMessage)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_forecast_toolbar, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId) {
            R.id.item_set_current -> {
                viewModel.changeCurrentCity()
                true
            }
            R.id.item_share -> {
                shareWeatherForecast()
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.item_set_current)
        item.setIcon(if (viewModel.isCityCurrent.value == true) {
            R.drawable.ic_current_city_active
        } else {
            R.drawable.ic_current_city
        })
    }

    private fun showMessage(messageConfig: MessageConfig) {
        val duration = if (messageConfig.isInfinite) {
            Snackbar.LENGTH_INDEFINITE
        } else {
            Snackbar.LENGTH_SHORT
        }
        val snackbar = Snackbar.make(binding.root, messageConfig.message, duration)
        val actionConfig = messageConfig.actionConfig
        if (actionConfig != null) {
            snackbar.setAction(actionConfig.title) {
                actionConfig.action.invoke()
            }
        }
        snackbar.show()
    }

    private fun shareWeatherForecast() {
        val forecastText = viewModel.getWeatherForecastText()
        if (forecastText == null) {
            showMessage(MessageConfig(R.string.share_error))
            return
        }

        val sendIntent: Intent = Intent().apply {
            action = Intent.ACTION_SEND
            putExtra(Intent.EXTRA_TEXT, forecastText)
            type = "text/plain"
        }
        startActivity(Intent.createChooser(sendIntent, null))
    }
}