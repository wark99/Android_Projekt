package com.example.projekt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class RecyclerViewFavouriteAdapter(private var dataset: ArrayList<String>) :
    RecyclerView.Adapter<RecyclerViewFavouriteAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        var restaurantName: TextView = view.findViewById(R.id.nameTextView)
        var favouriteButton: ImageButton = view.findViewById(R.id.profileFavouriteImageButton)
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_itme_profile_screen, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.restaurantName.text = dataset[position]
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}