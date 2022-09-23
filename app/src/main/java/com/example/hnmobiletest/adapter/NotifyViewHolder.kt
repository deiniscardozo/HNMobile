package com.example.hnmobiletest.adapter

import android.util.Log
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.hnmobiletest.Hit
import com.example.hnmobiletest.databinding.ItemNotifyBinding


class NotifyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemNotifyBinding.bind(view)

    fun render(hitModel: Hit, onClickListener:(Hit) -> Unit) {
        Log.i("Deinis", hitModel.toString())

        binding.tvTitular.text = hitModel.story_title
        binding.tvAuthor.text = hitModel.author
        binding.tvCreatedAt.text = hitModel.created_at

        //itemView.setOnClickListener { onClickListener(thirdsModel) }

    }
}