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

    fun deleteAll()=viewModelScope.launch {
        repository.deleteAll()
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