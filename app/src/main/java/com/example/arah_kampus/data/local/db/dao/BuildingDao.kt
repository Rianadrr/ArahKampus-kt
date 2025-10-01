package com.example.arah_kampus.data.local.db.dao

import androidx.room.*
import com.example.arah_kampus.data.local.db.entity.Building

@Dao
interface BuildingDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upsert(building: Building)

    @Update
    suspend fun update(building: Building)

    @Query("SELECT * FROM buildings WHERE favorite = 1")
    suspend fun getFavorites(): List<Building>

    @Query("SELECT * FROM buildings WHERE id = :id LIMIT 1")
    suspend fun getBuildingById(id: Int): Building?

}
