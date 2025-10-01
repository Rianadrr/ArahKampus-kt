package com.example.arah_kampus.data.local.db.entity

import androidx.room.*

@Entity(tableName = "buildings")
data class Building(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "name")
    val name: String,

    @ColumnInfo(name = "lat")
    val lat: Double,

    @ColumnInfo(name = "lng")
    val lng: Double,

    @ColumnInfo(name = "favorite")
    val favorite: Boolean = false
)
