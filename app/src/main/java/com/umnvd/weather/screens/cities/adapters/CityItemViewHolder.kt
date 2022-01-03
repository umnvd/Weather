package com.umnvd.weather.screens.cities.adapters

import androidx.recyclerview.widget.RecyclerView
import com.umnvd.weather.R
import com.umnvd.weather.databinding.ItemCityBinding
import com.umnvd.weather.models.CitiesListItem

class CityItemViewHolder(
    private val binding: ItemCityBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(cityItem: CitiesListItem) {
        binding.itemCityNameTextView.text = cityItem.name

        binding.itemCityIsCurrentImageView.setImageResource(
            if (cityItem.isCurrent) {
                R.drawable.ic_current_city_active
            } else {
                R.drawable.ic_current_city
            }
        )
    }
}