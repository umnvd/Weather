package com.umnvd.weather.screens.cities.adapters

import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class CityItemTouchCallback(
    private val listener: Listener
) : ItemTouchHelper.Callback() {

    private var fromPosition: Int = -1
    private var toPosition: Int = -1

    override fun getMovementFlags(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder
    ): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        return makeMovementFlags(dragFlags, 0)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return true
    }

    override fun onMove(
        recyclerView: RecyclerView,
        viewHolder: RecyclerView.ViewHolder,
        target: RecyclerView.ViewHolder
    ): Boolean {
        toPosition = target.bindingAdapterPosition
        return listener.onDrag(viewHolder.bindingAdapterPosition, target.bindingAdapterPosition)
    }

    override fun onSelectedChanged(viewHolder: RecyclerView.ViewHolder?, actionState: Int) {
        super.onSelectedChanged(viewHolder, actionState)

        when (actionState) {
            ItemTouchHelper.ACTION_STATE_DRAG -> {
                fromPosition = viewHolder?.bindingAdapterPosition ?: -1
            }
            ItemTouchHelper.ACTION_STATE_IDLE -> {
                if (fromPosition == toPosition) return
                if (fromPosition == -1 || toPosition == -1) return

                listener.onDragged(fromPosition, toPosition)

                fromPosition = -1
                toPosition = -1
            }
        }
    }

    // Unused
    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {}

    interface Listener {
        fun onDrag(fromPosition: Int, toPosition: Int): Boolean
        fun onDragged(fromPosition: Int, toPosition: Int)
    }
}