package com.example.hnmobiletest.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hnmobiletest.model.dataclass.Hit

@Database(entities = [Hit::class], version = 1)
abstract class HitListDatabase : RoomDatabase() {
    abstract fun hitDao(): HitDao
}