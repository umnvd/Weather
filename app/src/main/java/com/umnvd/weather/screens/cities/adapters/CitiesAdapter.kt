package com.umnvd.weather.screens.cities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.umnvd.weather.databinding.ItemCityBinding
import com.umnvd.weather.models.CitiesListItem
import java.util.*

class CitiesAdapter(
    private val listener: Listener
) : ListAdapter<CitiesListItem, CityItemViewHolder>(CityItemDiffCallback()),
    CityItemTouchCallback.Listener {

    private var cities: MutableList<CitiesListItem> = mutableListOf()

    fun setCities(cities: List<CitiesListItem>) {
        this.cities = cities.toMutableList()
        submitList(cities)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        val viewHolder = CityItemViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onCityItemClick(getItem(viewHolder.adapterPosition))
            }
            itemCityIsCurrentImageView.setOnClickListener {
                listener.onCityIsCurrentClick(getItem(viewHolder.adapterPosition))
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onDrag(fromPosition: Int, toPosition: Int): Boolean {
        if (fromPosition < toPosition) {
            for (i in fromPosition until toPosition) {
                Collections.swap(cities, i, i + 1)
            }
        } else {
            for (i in fromPosition downTo toPosition + 1) {
                Collections.swap(cities, i, i - 1)
            }
        }
        notifyItemMoved(fromPosition, toPosition)
        return true
    }

    override fun onDragged(fromPosition: Int, toPosition: Int) {
        listener.onCityItemMove(fromPosition, toPosition)
    }

    interface Listener {
        fun onCityIsCurrentClick(cityItem: CitiesListItem)
        fun onCityItemClick(cityItem: CitiesListItem)
        fun onCityItemMove(fromPosition: Int, toPosition: Int)
    }
}