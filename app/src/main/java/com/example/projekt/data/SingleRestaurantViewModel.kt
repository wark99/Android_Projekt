package com.example.projekt.data

import androidx.lifecycle.*
import kotlinx.coroutines.launch

class SingleRestaurantViewModel(private val repository: RestaurantRepository) : ViewModel() {

    val allRestaurants: LiveData<List<Restaurant>> = repository.allRestaurant

    private val clickedRestaurant = MutableLiveData<RestaurantData>()
    val selectedItem: LiveData<RestaurantData> get() = clickedRestaurant

    fun selectedItem(restaurantData: RestaurantData) {
        clickedRestaurant.value = restaurantData
    }

    fun updateFavourite(id: Int, favourite: Boolean, dataListener: DataListener) = viewModelScope.launch {
        repository.updateFavourite(id, favourite, dataListener)
    }
}

class SingleRestaurantViewModelFactory(private val repository: RestaurantRepository) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SingleRestaurantViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return SingleRestaurantViewModel(repository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}