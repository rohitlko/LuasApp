package com.rohit.luasapp.binding

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

/**
 * Binding adapters are responsible for making the appropriate framework calls to set values.
 * In this file we are creating binding adapters for the RecyclerView.
 */
object RecyclerViewBindingAdapter {

    /**
     * Method to set the RecyclerView adapter from the XML using the 'setAdapter' attribute.
     *
     * @param adapter
     */
    @JvmStatic
    @BindingAdapter("setAdapter")
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

}