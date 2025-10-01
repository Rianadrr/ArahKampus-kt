package com.example.arah_kampus.data.local.db.entity

import androidx.room.*

@Entity(tableName = "navlogs")
data class NavLog(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,

    @ColumnInfo(name = "building_name")
    val buildingName: String,

    @ColumnInfo(name = "opened")
    val opened: Boolean,

    @ColumnInfo(name = "timestamp")
    val timestamp: Long
)
