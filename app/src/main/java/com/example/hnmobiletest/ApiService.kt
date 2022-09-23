package com.example.hnmobiletest

import retrofit2.Call
import retrofit2.http.*

interface ApiService {

    @GET("api/v1/search_by_date")
    fun listNotify(@Query("query") query: String) : Call<DataClass>
}


