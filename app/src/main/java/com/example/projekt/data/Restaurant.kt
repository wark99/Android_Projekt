package com.example.projekt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "restaurants_table")
data class Restaurant(
    @PrimaryKey
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "city") val city: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "area") val area: String,
    @ColumnInfo(name = "postal_code") val postal_code: String,
    @ColumnInfo(name = "country") val country: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "lat") val lat: Double,
    @ColumnInfo(name = "lng") val lng: Double,
    @ColumnInfo(name = "price") val price: Double,
    @ColumnInfo(name = "reserve_url") val reserve_url: String,
    @ColumnInfo(name = "mobile_reserve_url") val mobile_reserve_url: String,
    @ColumnInfo(name = "image_url") val image_url: String
)