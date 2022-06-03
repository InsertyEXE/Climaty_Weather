package com.example.climatyweather.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.climatyweather.R
import kotlinx.android.synthetic.main.item_result.view.*

class SearchAdapter(val lista: List<WeatherApiResult>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val cityName = itemView.item_txt_city
        val country = itemView.item_txt_country
        val status = itemView.item_txt_status
        val temp = itemView.item_txt_temp
        val thumbnail = itemView.item_img_temp

        fun bind(city: WeatherApiResult){

            thumbnail.setImageResource(R.drawable.sun)
            cityName.text = city.name
            country.text = city.sys.country
            status.text = city.weather[0].main
            temp.text = city.main.temp.toString()
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
                holder.bind(lista[position])
            }
        }
    }

    override fun getItemCount() = lista.size
}