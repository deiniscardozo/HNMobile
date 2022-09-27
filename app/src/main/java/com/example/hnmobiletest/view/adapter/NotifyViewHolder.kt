package com.example.hnmobiletest.view.adapter

import android.annotation.SuppressLint
import android.os.Build
import android.view.View
import androidx.annotation.RequiresApi
import androidx.recyclerview.widget.RecyclerView
import com.example.hnmobiletest.model.dataclass.Hit
import com.example.hnmobiletest.databinding.ItemNotifyBinding
import java.text.SimpleDateFormat
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter


class NotifyViewHolder(view: View): RecyclerView.ViewHolder(view) {

    private val binding = ItemNotifyBinding.bind(view)

    @SuppressLint("SimpleDateFormat")
    @RequiresApi(Build.VERSION_CODES.O)
    fun render(hitModel:Hit, onClickListener: (Hit) -> Unit) {
        val dateString = hitModel.created_at
        val cal = LocalDateTime.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
        val date = LocalDate.parse(dateString, formatter)

        binding.tvTitular.text = hitModel.story_title
        binding.tvAuthor.text = hitModel.author

        if(cal.year == date.year && cal.monthValue == date.monthValue &&
            cal.dayOfMonth.minus(1) == date.dayOfMonth) {

             binding.tvCreatedAt.text = "yesterday"

            } else if(cal.year == date.year && cal.monthValue == date.monthValue &&
            cal.dayOfMonth == date.dayOfMonth) {

            val dfs = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'")
            val dfsc = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS")
            val begin = dfs.parse(dateString)
            val end = dfsc.parse(cal.toString())
            val between: Long = (end.time - begin.time) / 1000

            val day: Long = between / (24 * 3600)
            val hour: Long = between % (24 * 3600) / 3600
            val minute: Long = between % 3600 / 60

            if(day in 2..7){
                binding.tvCreatedAt.text = day.toString() + "d"
            } else if(hour in 1..24){
                binding.tvCreatedAt.text = hour.toString() + "h"
            } else if(minute in 1..60){
                binding.tvCreatedAt.text = minute.toString() + "m"
            }

            } else {

                binding.tvCreatedAt.text = date.toString()
            }

        itemView.setOnClickListener { onClickListener(hitModel) }
    }
}
