package com.example.hnmobiletest.model.database

import androidx.annotation.WorkerThread
import androidx.room.withTransaction
import com.example.hnmobiletest.model.api.ApiService
import com.example.hnmobiletest.model.dataclass.DataClass
import com.example.hnmobiletest.model.dataclass.Hit
import com.example.hnmobiletest.util.networkBoundResource
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import javax.inject.Inject

class HitListRepository @Inject constructor(
    private val api: ApiService,
    private val db: HitListDatabase
) {
    private val hitsDao = db.hitDao()
    var listNotify: MutableList<Hit> = mutableListOf()
    var dataItem: DataClass? = null

    fun getHits() = networkBoundResource(
        query = {
            hitsDao.getAllHits()
        },
        fetch = {
            delay(2000)
            api.listNotify("mobile").enqueue(object : Callback<DataClass> {
                override fun onResponse(call:Call<DataClass>, response:Response<DataClass>) {
                    if (response.isSuccessful) {

                        dataItem = response.body()

                        listNotify.clear()
                        listNotify.addAll(response.body()!!.hits)
                    }
                }

                override fun onFailure(call:Call<DataClass>, t:Throwable) {
                }
            })
        },
        saveFetchResult = {
            db.withTransaction {
                hitsDao.deleteAllHits()
                hitsDao.insertHits(listNotify)
            }
        }
    )
}