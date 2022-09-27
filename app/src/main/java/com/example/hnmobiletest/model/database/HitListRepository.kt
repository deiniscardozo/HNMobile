package com.example.hnmobiletest.model.database

import androidx.room.withTransaction
import com.example.hnmobiletest.model.api.ApiService
import com.example.hnmobiletest.model.dataclass.Hit
import com.example.hnmobiletest.util.networkBoundResource
import kotlinx.coroutines.delay
import javax.inject.Inject

class HitListRepository @Inject constructor(
    private val api: ApiService,
    private val db: HitListDatabase
) {
    private val hitsDao = db.hitDao()

    fun getHits() = networkBoundResource(
        query = {
            hitsDao.getAllHits()
        },
        fetch = {
            delay(2000)
           api.listHit("mobile")
        },
        saveFetchResult = { HitList: List<Hit> ->
            db.withTransaction {
                hitsDao.deleteAllHits()
                hitsDao.insertHits(HitList)
            }
        }
    )
}