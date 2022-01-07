package com.umnvd.weather.screens.cities.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.umnvd.weather.databinding.ItemCityBinding
import com.umnvd.weather.models.CitiesListItem
import java.util.*

class CitiesAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<CityItemViewHolder>(),
    CityItemTouchCallback.Listener {

    private var cities: MutableList<CitiesListItem> = mutableListOf()

    init {
        stateRestorationPolicy = StateRestorationPolicy.PREVENT_WHEN_EMPTY
    }

    fun setCities(newCities: List<CitiesListItem>) {
        val callback = CityItemDiffCallback(cities, newCities)
        val diff = DiffUtil.calculateDiff(callback, false)
        cities = newCities.toMutableList()
        diff.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return cities.size
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        val viewHolder = CityItemViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onCityItemClick(cities[viewHolder.bindingAdapterPosition])
            }
            itemCityIsCurrentImageView.setOnClickListener {
                listener.onCityIsCurrentClick(cities[viewHolder.bindingAdapterPosition])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        holder.bind(cities[position])
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