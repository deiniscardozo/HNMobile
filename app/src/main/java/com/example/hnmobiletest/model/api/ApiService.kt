package com.example.hnmobiletest.model.api

import com.example.hnmobiletest.model.dataclass.DataClass
import com.example.hnmobiletest.model.dataclass.Hit
import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/v1/search_by_date")
    fun listNotify(@Query("query") query: String) :Call<DataClass>

    @GET("api/v1/search_by_date")
    fun listHit(@Query("query") query: String) :List<Hit>

}