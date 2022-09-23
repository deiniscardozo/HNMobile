package com.example.hnmobiletest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.hnmobiletest.adapter.NotifyAdapter
import com.example.hnmobiletest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listNotify: MutableList<Hit> = mutableListOf()

    override fun onCreate(savedInstanceState:Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notifyCall()
    }

    fun notifyCall() {

        val apiService: ApiService = ApiAdapter.getApiAdapter().create(ApiService::class.java)
        val resultThird:Call<DataClass> = apiService.listNotify("mobile")

        resultThird.enqueue(object : Callback<DataClass> {

            override fun onResponse(
                call:Call<DataClass>,
                response:Response<DataClass>
            ) {
                if(response.isSuccessful){
                    Log.i("Deinisresponse", response.body().toString())

                    listNotify.clear()
                    listNotify.addAll(response.body()!!.hits)

                    initRecyclerView()
                }
            }

            override fun onFailure(call:Call<DataClass>, t: Throwable) {

            }
        })
    }

    private fun initRecyclerView(){

        val decoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.reciclerNotify.isVisible = true
        binding.reciclerNotify.layoutManager = LinearLayoutManager(this)
        binding.reciclerNotify.adapter = NotifyAdapter(listNotify) {
        }

        binding.reciclerNotify.addItemDecoration(decoration)

    }
}