package com.example.projekt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Profile::class], version = 1)
abstract class ProfileRoomDatabase : RoomDatabase() {

    abstract fun profileDao(): ProfileDao

    companion object {
        @Volatile
        private var INSTANCE: ProfileRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): ProfileRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ProfileRoomDatabase::class.java,
                    "profile_databse"
                ).fallbackToDestructiveMigration().addCallback(ProfileRoomDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class ProfileRoomDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                        deleteAll()
                    }
                }
            }
        }

        private fun deleteAll() {
            this.deleteAll()
        }
    }
}