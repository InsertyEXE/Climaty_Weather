package com.example.climatyweather

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.item_result.view.*

class SearchAdapter: RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val cityName = itemView.item_txt_city
        val country = itemView.item_txt_country
        val status = itemView.item_txt_status
        val temp = itemView.item_txt_temp
        val thumbnail = itemView.item_img_temp

        fun bind(){

            thumbnail.setImageResource(R.drawable.sun)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return ResultViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_result, parent, false)
        )
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is ResultViewHolder -> {
                holder.bind()
            }
        }
    }

    override fun getItemCount() = 15
}