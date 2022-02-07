package com.rohit.luasapp

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.rohit.luasapp.databinding.ItemTramBinding
import com.rohit.luasapp.data.Tram

class LuasAdapter : RecyclerView.Adapter<LuasAdapter.ViewHolder>() {

    private val items: MutableList<Tram> = mutableListOf()

    fun setItems(tramsList: List<Tram>) {
        items.clear()
        items.addAll(tramsList)
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemTramBinding.inflate(layoutInflater, parent, false)

        return ViewHolder(binding)
    }

    override fun getItemCount() = items.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.binding.apply {
            tram = items[position]

            executePendingBindings()
        }
    }

    class ViewHolder(val binding: ItemTramBinding): RecyclerView.ViewHolder(binding.root)

}