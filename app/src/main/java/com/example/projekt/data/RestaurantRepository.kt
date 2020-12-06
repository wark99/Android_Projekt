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

    suspend fun deleteAll() {
        restaurantDao.deleteAll()
    }

    suspend fun updateFavourite(id: Int, favourite: Boolean, dataListener: DataListener) {
        restaurantDao.updateFavourite(id, favourite, dataListener)
    }

    suspend fun updateImage(id: Int, image_url: String, dataListener: DataListener) {
        restaurantDao.updateImage(id, image_url, dataListener)
    }

}