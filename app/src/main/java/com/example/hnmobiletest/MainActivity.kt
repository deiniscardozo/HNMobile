package com.example.hnmobiletest

import android.R
import android.content.Intent
import android.graphics.Canvas
import android.os.Bundle
import android.view.textclassifier.TextSelection
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.hnmobiletest.adapter.NotifyAdapter
import com.example.hnmobiletest.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    var listNotify: MutableList<Hit> = mutableListOf()
    var dataItem: DataClass? = null
    private lateinit var url: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        notifyCall()

        binding.swipeRefreshLayout.setOnRefreshListener {
            notifyCall()
            binding.swipeRefreshLayout.isRefreshing = false
        }
    }

    fun notifyCall() {

        val apiService: ApiService = ApiAdapter.getApiAdapter().create(ApiService::class.java)
        val resultThird: Call<DataClass> = apiService.listNotify("mobile")

        resultThird.enqueue(object : Callback<DataClass> {

            override fun onResponse(
                call: Call<DataClass>,
                response: Response<DataClass>
            ) {
                if (response.isSuccessful) {

                    dataItem = response.body()

                    listNotify.clear()
                    listNotify.addAll(response.body()!!.hits)

                    initRecyclerView()

                    val callback: ItemTouchHelper.SimpleCallback = object :
                        ItemTouchHelper.SimpleCallback(
                            0,
                            ItemTouchHelper.RIGHT or ItemTouchHelper.LEFT
                        ) {
                        override fun onMove(
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            target: RecyclerView.ViewHolder
                        ): Boolean {
                            return false
                        }

                        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                            // Take action for the swiped item
                        }

                        override fun onChildDraw(
                            c: Canvas,
                            recyclerView: RecyclerView,
                            viewHolder: RecyclerView.ViewHolder,
                            dX: Float,
                            dY: Float,
                            actionState: Int,
                            isCurrentlyActive: Boolean
                        )
                        {

                            super.onChildDraw(
                                c,
                                recyclerView!!,
                                viewHolder!!,
                                dX,
                                dY,
                                actionState,
                                isCurrentlyActive
                            )
                        }
                    }
                    val itemTouchHelper = ItemTouchHelper(callback)
                    itemTouchHelper.attachToRecyclerView(binding.reciclerNotify)
                }
            }

            override fun onFailure(call: Call<DataClass>, t: Throwable) {

            }
        })
    }

    private fun initRecyclerView() {
        val decoration = DividerItemDecoration(this, LinearLayoutManager(this).orientation)

        binding.reciclerNotify.isVisible = true
        binding.reciclerNotify.layoutManager = LinearLayoutManager(this)
        binding.reciclerNotify.adapter = NotifyAdapter(listNotify) {
            url = it._highlightResult.story_url.value
            intentActivity()
        }

        binding.reciclerNotify.addItemDecoration(decoration)

    }

    fun intentActivity() {
        val intent = Intent(this, WebViewActivity::class.java)
        intent.putExtra("url", url)

        startActivity(intent)
        finish()
    }

}