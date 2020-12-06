package com.example.projekt.data

import androidx.lifecycle.LiveData

class ProfileRepository(private val profileDao: ProfileDao) {

    val getProfile: LiveData<Profile> = profileDao.getProfile()

    suspend fun insertProfile(profile: Profile){
        profileDao.insertProfile(profile)
    }

    suspend fun updateProfile(profile: Profile) {
        profileDao.updateProfile(profile)
    }

    suspend fun deleteProfile() {
        profileDao.deleteProfile()
    }
}