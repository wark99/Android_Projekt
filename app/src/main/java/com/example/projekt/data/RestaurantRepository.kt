package com.example.projekt.data

import androidx.annotation.WorkerThread
import androidx.lifecycle.LiveData

class RestaurantRepository(private val restaurantDao: RestaurantDao) {

    val allRestaurant: LiveData<List<Restaurant>> = restaurantDao.getRestaurants()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(restaurant: Restaurant) {
        restaurantDao.insert(restaurant)
    }

    suspend fun deleteAll(){
        restaurantDao.deleteAll()
    }
}