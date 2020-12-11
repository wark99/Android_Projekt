package com.example.projekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class RestaurantViewModel(private val repository: RestaurantRepository) : ViewModel() {

    val allRestaurants: LiveData<List<Restaurant>> = repository.allRestaurant

    fun insert(restaurant: Restaurant) = viewModelScope.launch {
        repository.insert(restaurant)
    }

    fun deleteAll() = viewModelScope.launch {
        repository.deleteAll()
    }

    fun updateFavourite(id: Int, favourite: Boolean, dataListener: DataListener) = viewModelScope.launch {
        repository.updateFavourite(id, favourite, dataListener)
    }

    fun updateImage(id: Int, image_url: String, dataListener: DataListener) = viewModelScope.launch {
        repository.updateImage(id, image_url, dataListener)
    }
}

class RestaurantViewModelFactory(private val repository: RestaurantRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(RestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return RestaurantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}