package com.example.projekt.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface RestaurantDao {

    @Query("SELECT * FROM restaurants_table")
    fun getRestaurants(): LiveData<List<Restaurant>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(restaurant: Restaurant)

    @Query("DELETE FROM restaurants_table")
    suspend fun deleteAll()
}