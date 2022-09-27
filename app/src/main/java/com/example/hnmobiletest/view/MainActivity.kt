package com.example.hnmobiletest.view

import android.R
import android.content.Intent
import android.graphics.*
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.recyclerview.widget.*
import com.example.hnmobiletest.view.adapter.NotifyAdapter
import com.example.hnmobiletest.databinding.ActivityMainBinding
import com.example.hnmobiletest.model.api.ApiAdapter
import com.example.hnmobiletest.model.api.ApiService
import com.example.hnmobiletest.model.dataclass.DataClass
import com.example.hnmobiletest.model.dataclass.Hit
import com.google.android.material.snackbar.Snackbar
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

        val apiService:ApiService = ApiAdapter.getApiAdapter().create(ApiService::class.java)
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
                            val deletedHit:Hit = listNotify[viewHolder.adapterPosition]
                            val position = viewHolder.adapterPosition
                            val mAdapter = NotifyAdapter(listNotify) {
                                url = it._highlightResult.story_url.value
                                intentActivity()
                            }

                            listNotify.removeAt(viewHolder.adapterPosition)
                            mAdapter.notifyItemRemoved(viewHolder.adapterPosition)
                            Snackbar.make(binding.reciclerNotify, "Deleted " + deletedHit.story_title, Snackbar.LENGTH_LONG)
                                .setAction(
                                    "Undo",
                                    View.OnClickListener {
                                        listNotify.add(position, deletedHit)
                                        mAdapter.notifyItemInserted(position)
                                    }).show()
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
                            val icon:Bitmap
                            if (actionState == ItemTouchHelper.ACTION_STATE_SWIPE) {

                                val itemView = viewHolder.itemView
                                val height = itemView.bottom.toFloat() - itemView.top.toFloat()
                                val width = height / 3

                                if (dX > 0) {
                                    Paint().color = Color.parseColor("#388E3C")
                                    val background = RectF(itemView.left.toFloat(), itemView.top.toFloat(), dX, itemView.bottom.toFloat())
                                    c.drawRect(background, Paint())
                                    icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                                    val icon_dest = RectF(
                                        itemView.left.toFloat() + width,
                                        itemView.top.toFloat() + width,
                                        itemView.left.toFloat() + 2 * width,
                                        itemView.bottom.toFloat() - width
                                    )
                                    c.drawBitmap(icon, null, icon_dest, Paint())
                                } else {
                                    Paint().color = Color.parseColor("#D32F2F")
                                    val background = RectF(
                                        itemView.right.toFloat() + dX,
                                        itemView.top.toFloat(),
                                        itemView.right.toFloat(),
                                        itemView.bottom.toFloat()
                                    )
                                    c.drawRect(background, Paint())
                                    icon = BitmapFactory.decodeResource(resources, R.drawable.ic_delete)
                                    val icon_dest = RectF(
                                        itemView.right.toFloat() - 2 * width,
                                        itemView.top.toFloat() + width,
                                        itemView.right.toFloat() - width,
                                        itemView.bottom.toFloat() - width
                                    )
                                    c.drawBitmap(icon, null, icon_dest, Paint())
                                }
                            }
                            super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
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