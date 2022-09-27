package com.example.hnmobiletest.model.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.hnmobiletest.model.dataclass.DataClass

@Database(entities = [DataClass::class], version = 1)
abstract class HitListDatabase : RoomDatabase() {
    abstract fun hitDao(): HitDao
}