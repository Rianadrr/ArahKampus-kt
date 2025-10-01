package com.example.arah_kampus.data.local.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.arah_kampus.data.local.db.dao.BuildingDao
import com.example.arah_kampus.data.local.db.dao.NavLogDao
import com.example.arah_kampus.data.local.db.entity.Building
import com.example.arah_kampus.data.local.db.entity.NavLog

@Database(
    entities = [Building::class, NavLog::class],
    version = 2, // Naikkan versinya kalau ada perubahan entity
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun buildingDao(): BuildingDao
    abstract fun navLogDao(): NavLogDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun get(context: Context): AppDatabase {
            return INSTANCE ?: synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "arah-kampus.db"
                )
                    .fallbackToDestructiveMigration() // reset otomatis kalau ada schema mismatch
                    .build()
                INSTANCE = instance
                instance
            }
        }
    }
}
