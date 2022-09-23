package com.example.hnmobiletest.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.hnmobiletest.Hit
import com.example.hnmobiletest.R

class NotifyAdapter(private val listNotify: List<Hit>,
                    private val onClickListener:(Hit) -> Unit) :
    RecyclerView.Adapter<NotifyViewHolder>(){

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int):NotifyViewHolder {
        Log.i("Deinis", "entro")
        val layoutInflater = LayoutInflater.from(parent.context)
        return NotifyViewHolder(layoutInflater.inflate(
            R.layout.item_notify, parent
            , false))
    }

    override fun onBindViewHolder(holder:NotifyViewHolder, position: Int) {
        Log.i("Deinis1", listNotify.toString())
        val item = listNotify[position]
        holder.render(item, onClickListener)

    }
    override fun getItemCount(): Int = listNotify.size

}