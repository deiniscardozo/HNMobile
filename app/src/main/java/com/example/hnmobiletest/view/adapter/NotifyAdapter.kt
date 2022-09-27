package com.example.hnmobiletest.view.adapter

import android.os.Build
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hnmobiletest.model.dataclass.Hit
import com.example.hnmobiletest.R

class NotifyAdapter(private val listNotify: List<Hit>,
                    private val onClickListener:(Hit) -> Unit) :
    RecyclerView.Adapter<NotifyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NotifyViewHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotifyViewHolder(layoutInflater.inflate(
            R.layout.item_notify, parent
            , false))
    }

    @RequiresApi(Build.VERSION_CODES.O)
    override fun onBindViewHolder(holder:NotifyViewHolder, position: Int) {
        val item = listNotify[position]
        holder.render(item, onClickListener)

    }
    override fun getItemCount(): Int = listNotify.size

}