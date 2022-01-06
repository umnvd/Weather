package com.umnvd.weather.screens.cities.adapters

import androidx.recyclerview.widget.DiffUtil
import com.umnvd.weather.models.CitiesListItem

class CityItemDiffCallback(
    private val oldCities: List<CitiesListItem>,
    private val newCities: List<CitiesListItem>
) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldCities.size

    override fun getNewListSize(): Int = newCities.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCities[oldItemPosition].id == newCities[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldCities[oldItemPosition] == newCities[newItemPosition]
    }
}