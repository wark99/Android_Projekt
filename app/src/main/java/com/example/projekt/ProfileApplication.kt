package com.example.projekt

import android.app.Application
import com.example.projekt.data.ProfileRepository
import com.example.projekt.data.ProfileRoomDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob

class ProfileApplication : Application() {
    val applicationScope = CoroutineScope(SupervisorJob())


}