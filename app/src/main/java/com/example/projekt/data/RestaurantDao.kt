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

    @Query("UPDATE restaurants_table SET favourite=:newFavourite WHERE id=:newId")
    suspend fun updateFavourite(newId: Int, newFavourite: Boolean, dataListener: DataListener) {
        dataListener.onDataReady()
    }

    @Query("UPDATE restaurants_table SET image_url=:newImage WHERE id=:newId")
    suspend fun updateImage(newId: Int, newImage: String, dataListener: DataListener) {
        dataListener.onDataReady()
    }
}