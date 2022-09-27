package com.example.hnmobiletest.model.database

import androidx.annotation.WorkerThread
import com.example.hnmobiletest.model.dataclass.Hit
import kotlinx.coroutines.flow.Flow

class HitListRepository(private val hitDao:HitDao) {

   val allHits:Flow<List<Hit>> = hitDao.getAllHits()

    @Suppress("RedundantSuspendModifier")
    @WorkerThread
    suspend fun insert(hit:List<Hit>) {
        hitDao.insertHits(hit)
    }
}