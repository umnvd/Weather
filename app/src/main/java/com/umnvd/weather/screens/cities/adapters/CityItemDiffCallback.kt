package com.umnvd.weather.screens.cities.adapters

import androidx.recyclerview.widget.DiffUtil
import com.umnvd.weather.models.CitiesListItem

class CityItemDiffCallback : DiffUtil.ItemCallback<CitiesListItem>() {

    override fun areItemsTheSame(oldItem: CitiesListItem, newItem: CitiesListItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: CitiesListItem, newItem: CitiesListItem): Boolean {
        return oldItem == newItem
    }

}