package com.example.projekt

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.projekt.data.RestaurantData

class RecyclerViewFavouriteAdapter(
    private var dataset: ArrayList<RestaurantData>,
    private val favouriteClickListener: OnFavouriteClickListener
) :
    RecyclerView.Adapter<RecyclerViewFavouriteAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        var restaurantName: TextView = view.findViewById(R.id.nameTextView)
        var favouriteButton: ImageButton = view.findViewById(R.id.profileFavouriteImageButton)

        init {
            favouriteButton.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                favouriteClickListener.onFavouriteClick(position)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_itme_profile_screen, viewGroup, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.restaurantName.text = dataset[position].getName()
        viewHolder.favouriteButton.setImageResource(R.drawable.favorite)
    }

    interface OnFavouriteClickListener {
        fun onFavouriteClick(position: Int)
    }

    override fun getItemCount(): Int {
        return dataset.size
    }
}