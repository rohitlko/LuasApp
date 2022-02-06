package com.rohit.luasapp.ui.forecast

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohit.luasapp.databinding.ItemTramBinding
import com.rohit.luasapp.model.Tram

/**
 * Tram list adapter responsible to render the list items with its respective data.
 * Extends from RecyclerView.Adapter using a custom ViewHolder
 *
 */
class TramAdapter : RecyclerView.Adapter<TramAdapter.ViewHolder>() {

    // Holds the list of trams to be displayed
    private val items: MutableList<Tram> = mutableListOf()

    /**
     * Updates the items list with the new data and notifies the view to reload.
     *
     * @param tramsList
     */
    fun setItems(tramsList: List<Tram>) {
        // Clean the old items in the list to avoid duplications
        items.clear()

        // Add all items again to the list
        items.addAll(tramsList)

        // Notify any registered observers that the data set has changed so the view can be rendered again.
        notifyDataSetChanged()
    }

    /**
     * Inflates the view of ItemTramBinding and return the ViewHolder object.
     *
     * @param parent
     * @param viewType
     * @return TramAdapter ViewHolder
     */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTramBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    /**
     * Provides the size of items list.
     *
     */
    override fun getItemCount() = items.size


    /**
     * Bind each item of the items list to the ViewHolder.
     *
     * @param holder
     * @param position
     */
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tram = items[position]

            // Evaluates the pending bindings, updating any Views that have expressions bound to modified variables.
            executePendingBindings()
        }
    }

    /**
     * ViewHolder that describes an item view and metadata about its place within the RecyclerView.
     *
     * @property binding
     */
    class ViewHolder(val binding: ItemTramBinding): RecyclerView.ViewHolder(binding.root)

}