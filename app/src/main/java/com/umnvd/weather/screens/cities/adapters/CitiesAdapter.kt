package com.umnvd.weather.screens.cities.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.umnvd.weather.R
import com.umnvd.weather.databinding.ItemCityBinding
import com.umnvd.weather.models.CitiesListItem
import java.util.*

class CitiesAdapter(
    private val listener: Listener
) : RecyclerView.Adapter<CitiesAdapter.ViewHolder>(), CityItemTouchCallback.Listener {

    private var cities: MutableList<CitiesListItem> = mutableListOf()

    @SuppressLint("NotifyDataSetChanged")
    fun setCities(cities: List<CitiesListItem>) {
        this.cities = cities.toMutableList()
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int = cities.size

    override fun getItemId(position: Int): Long = cities[position].id

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemCityBinding.inflate(inflater, parent, false)
        val viewHolder = ViewHolder(binding)

        with(binding) {
            root.setOnClickListener {
                listener.onCityItemClick(cities[viewHolder.adapterPosition])
            }
            itemCityIsCurrentImageView.setOnClickListener {
                listener.onCityIsCurrentClick(cities[viewHolder.adapterPosition])
            }
        }

        return viewHolder
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
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

    class ViewHolder(private val binding: ItemCityBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(cityItem: CitiesListItem) {
            binding.itemCityNameTextView.text = cityItem.name
//            binding.itemCityIsCurrentImageView.visibility = if (cityItem.isCurrent) {
//                View.VISIBLE
//            } else {
//                View.GONE
//            }
            binding.itemCityIsCurrentImageView.setImageResource(
                if (cityItem.isCurrent) {
                    R.drawable.ic_current_city_active
                } else {
                    R.drawable.ic_current_city
                }
            )
        }
    }

    interface Listener {
        fun onCityIsCurrentClick(cityItem: CitiesListItem)
        fun onCityItemClick(cityItem: CitiesListItem)
        fun onCityItemMove(fromPosition: Int, toPosition: Int)
    }
}