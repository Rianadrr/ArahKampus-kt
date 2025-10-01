package com.example.arah_kampus.data.local.db.dao

import androidx.room.*
import com.example.arah_kampus.data.local.db.entity.NavLog

@Dao
interface NavLogDao {
    @Insert
    suspend fun insertLog(log: NavLog)

    @Query("SELECT COUNT(*) FROM navlogs")
    suspend fun getLogCount(): Int
}
