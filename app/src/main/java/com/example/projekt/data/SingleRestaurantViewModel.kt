package com.example.projekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class SingleRestaurantViewModel() : ViewModel() {

    private val clickedRestaurant = MutableLiveData<RestaurantData>()
    val selectedItem: LiveData<RestaurantData> get() = clickedRestaurant

    fun selectedItem(restaurantData: RestaurantData) {
        clickedRestaurant.value = restaurantData
    }
}

class SingleRestaurantViewModelFactory() : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleRestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingleRestaurantViewModel() as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}