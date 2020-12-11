package com.example.projekt.data

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ProfileDao {

    @Query("SELECT * FROM profile_table")
    fun getProfile(): LiveData<Profile>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertProfile(profile: Profile)

    @Update()
    suspend fun updateProfile(profile: Profile)

    @Query("DELETE FROM profile_table")
    suspend fun deleteProfile()
}