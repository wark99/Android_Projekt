package com.example.projekt.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profile_table")
data class Profile(
    @PrimaryKey
    @ColumnInfo(name = "avatar") val avatar: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "address") val address: String,
    @ColumnInfo(name = "phone") val phone: String,
    @ColumnInfo(name = "mail") val mail: String
)
