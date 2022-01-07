package com.umnvd.weather.screens.weather_forecast

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.tabs.TabLayoutMediator
import com.umnvd.weather.R
import com.umnvd.weather.databinding.FragmentWeatherForecastBinding
import com.umnvd.weather.di.AppComponent
import com.umnvd.weather.screens.BaseFragment
import com.umnvd.weather.screens.savedStateViewModels
import com.umnvd.weather.screens.weather_forecast.adapters.WeatherForecastAdapter
import com.umnvd.weather.utils.MessageConfig
import com.umnvd.weather.utils.observeEvent

class WeatherForecastFragment : BaseFragment(R.layout.fragment_weather_forecast) {

    private val viewModel: WeatherForecastViewModel by savedStateViewModels()

    private lateinit var binding: FragmentWeatherForecastBinding

    private val onPageChangeCallback =
        object : ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                viewModel.updateCurrentPage(position)
            }
        }

    override fun inject(appComponent: AppComponent) {
        appComponent.inject(this)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentWeatherForecastBinding.bind(view)
        setHasOptionsMenu(true)

        val adapter = WeatherForecastAdapter()
        with(binding) {
            forecastPager.adapter = adapter
            forecastPager.addItemDecoration(DividerItemDecoration(requireContext(), RecyclerView.HORIZONTAL))
            forecastPager.registerOnPageChangeCallback(onPageChangeCallback)

            TabLayoutMediator(
                forecastTabs,
                forecastPager,
                true,
            ) { tab, position ->
                tab.text = adapter.getPageTitle(position)
            }.attach()
        }

        viewModel.isCityCurrent.observe(this) { requireActivity().invalidateOptionsMenu() }
        viewModel.weatherForecast.observe(this, adapter::setWeatherForecast)
        viewModel.messageEvent.observeEvent(viewLifecycleOwner, ::showMessage)
        viewModel.currentPage.observe(viewLifecycleOwner, ::setCurrentPage)

    }

    override fun onDestroyView() {
        super.onDestroyView()
        binding.forecastPager.unregisterOnPageChangeCallback(onPageChangeCallback)
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_forecast_toolbar, menu)
    }

    override fun onPrepareOptionsMenu(menu: Menu) {
        super.onPrepareOptionsMenu(menu)
        val item = menu.findItem(R.id.item_set_current)
        item.setIcon(
            if (viewModel.isCityCurrent.value == true) {
                R.drawable.ic_current_city_active
            } else {
                R.drawable.ic_current_city
            }
        )
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
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

    private fun setCurrentPage(page: Int) {
        with(binding.forecastPager) {
            val smooth = currentItem == page
            setCurrentItem(page, smooth)
        }
    }
}