package com.example.hnmobiletest.model.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.hnmobiletest.model.dataclass.Hit
import kotlinx.coroutines.flow.Flow

@Dao
interface HitDao {

    @Query("SELECT * FROM hits")
    fun getAllHits():Flow<List<Hit>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHits(hit: List<Hit>)

    @Query("DELETE FROM hits")
    suspend fun deleteAllHits()
}
