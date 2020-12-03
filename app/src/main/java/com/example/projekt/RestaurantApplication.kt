package com.example.projekt

import android.app.Application
import com.example.projekt.data.RestaurantRepository
import com.example.projekt.data.RestaurantRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class RestaurantApplication : Application() {

    val applicationScope = CoroutineScope(SupervisorJob())

    val database by lazy { RestaurantRoomDatabase.getDatabase(this, applicationScope) }
    val repository by lazy { RestaurantRepository(database.restaurantDao()) }
}