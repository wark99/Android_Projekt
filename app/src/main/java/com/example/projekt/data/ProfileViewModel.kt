package com.example.projekt.data

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

class ProfileViewModel(private val profileRepository: ProfileRepository) : ViewModel() {

    val profile: LiveData<Profile> = profileRepository.getProfile

    fun insertProfile(profile: Profile) = viewModelScope.launch {
        profileRepository.insertProfile(profile)
    }

    fun updateProfile(profile: Profile) = viewModelScope.launch {
        profileRepository.updateProfile(profile)
    }

    fun deleteProfile() = viewModelScope.launch {
        profileRepository.deleteProfile()
    }
}

class ProfileViewModelFactory(private val profileRepository: ProfileRepository) :
    ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(ProfileViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return ProfileViewModel(profileRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}