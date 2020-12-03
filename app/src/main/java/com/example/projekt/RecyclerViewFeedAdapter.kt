package com.example.projekt

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.projekt.data.RestaurantData
import java.util.*
import kotlin.collections.ArrayList

class RecyclerViewFeedAdapter(
    private var dataSet: ArrayList<RestaurantData>,
    private val listener: OnItemClickListener
) :
    RecyclerView.Adapter<RecyclerViewFeedAdapter.ViewHolder>(), Filterable {

    private val filterList = ArrayList(dataSet)

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view), View.OnClickListener {
        val restaurantName: TextView = view.findViewById(R.id.titleTextView)
        val restaurantAddress: TextView = view.findViewById(R.id.addressTextView)
        val restaurantImage: ImageView = view.findViewById(R.id.placeHolderImageView)
        val restaurantPrice: TextView = view.findViewById(R.id.priceTextView)
        val restaurantFavouriteButton: ImageButton = view.findViewById(R.id.favouriteImageButton)

        init {
            view.setOnClickListener(this)
        }

        override fun onClick(view: View?) {
            val position: Int = adapterPosition
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(position)
            }
        }
    }

    override fun onCreateViewHolder(viewGroup: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(viewGroup.context)
            .inflate(R.layout.list_item_main_screen, viewGroup, false)
        return ViewHolder(view)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(viewHolder: ViewHolder, position: Int) {
        viewHolder.restaurantName.text = filterList[position].getName()
        viewHolder.restaurantAddress.text = "Address: " + filterList[position].getAddress()
        Glide.with(viewHolder.restaurantImage).load(filterList[position].getImage())
            .into(viewHolder.restaurantImage)
        viewHolder.restaurantPrice.text = "Price: " + filterList[position].getPrice()
        if (filterList[position].getFavourite()) {
            viewHolder.restaurantFavouriteButton.setBackgroundResource(R.drawable.favorite)
        }
    }

    override fun getItemCount(): Int {
        return filterList.size
    }

    interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    override fun getFilter(): Filter {
        return object : Filter() {
            private var filteredList = arrayListOf<RestaurantData>()
            override fun performFiltering(charSequence: CharSequence?): FilterResults {
                if (charSequence == null || charSequence.isEmpty()) {
                    filteredList.addAll(dataSet)
                } else {
                    val filterPattern = charSequence.toString().toLowerCase(Locale.ROOT).trim()

                    for (item: RestaurantData in filterList) {
                        if (item.getName().toLowerCase(Locale.ROOT).startsWith(filterPattern)) {
                            filteredList.add(item)
                        }
                    }
                }

                val results = FilterResults()
                results.values = filteredList
                return results
            }

            override fun publishResults(charSequence: CharSequence?, result: FilterResults?) {
                filterList.clear()
                filterList.addAll(result?.values as ArrayList<RestaurantData>)
                notifyDataSetChanged()
            }
        }
    }
}