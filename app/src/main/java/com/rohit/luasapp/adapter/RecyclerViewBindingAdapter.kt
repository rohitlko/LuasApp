package com.rohit.luasapp.adapter

import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView

object RecyclerViewBindingAdapter {

    @JvmStatic
    @BindingAdapter("setAdapter")
    fun RecyclerView.bindRecyclerViewAdapter(adapter: RecyclerView.Adapter<*>) {
        this.run {
            this.setHasFixedSize(true)
            this.adapter = adapter
        }
    }

}