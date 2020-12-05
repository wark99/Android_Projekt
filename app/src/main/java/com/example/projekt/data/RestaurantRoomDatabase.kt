package com.example.projekt.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [Restaurant::class], version = 2)
abstract class RestaurantRoomDatabase : RoomDatabase() {

    abstract fun restaurantDao(): RestaurantDao

    companion object {
        @Volatile
        private var INSTANCE: RestaurantRoomDatabase? = null

        fun getDatabase(context: Context, scope: CoroutineScope): RestaurantRoomDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    RestaurantRoomDatabase::class.java,
                    "restaurant_database"
                ).fallbackToDestructiveMigration()
                    .addCallback(RestaurantDatabaseCallback(scope))
                    .build()
                INSTANCE = instance
                instance
            }
        }

        private class RestaurantDatabaseCallback(private val scope: CoroutineScope) :
            RoomDatabase.Callback() {
            override fun onCreate(db: SupportSQLiteDatabase) {
                super.onCreate(db)
                INSTANCE?.let {
                    scope.launch(Dispatchers.IO) {
                        //deleteAll()
                    }
                }
            }
        }

        fun deleteAll() {
            this.deleteAll()
        }
    }
}