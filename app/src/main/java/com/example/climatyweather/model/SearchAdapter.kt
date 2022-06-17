package com.example.climatyweather.model

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.climatyweather.R
import kotlinx.android.synthetic.main.item_result.view.*
import kotlin.math.roundToInt

class SearchAdapter(val lista: List<WeatherApiResult>, private val onItemClicked: (WeatherApiResult) -> Unit): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    private class ResultViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val cityName = itemView.item_txt_city
        val country = itemView.item_txt_country
        val status = itemView.item_txt_status
        val temp = itemView.item_txt_temp
        val thumbnail = itemView.item_img_temp

        fun bind(city: WeatherApiResult, onItemClicked: (WeatherApiResult) -> Unit){

            cityName.text = city.name
            country.text = city.sys.country
            status.text = city.weather[0].main
            temp.text = "C° ${city.main.temp.roundToInt()}"

            when(city.weather[0].icon){
                "09d", "10d", "11d", "09n", "10n", "11n" -> thumbnail.setImageResource(R.drawable.rain)
                "01d" -> thumbnail.setImageResource(R.drawable.sun)
                "02d", "03d" -> thumbnail.setImageResource(R.drawable.sun_cloud)
                "01n" -> thumbnail.setImageResource(R.drawable.moon)
                "02n", "03n" -> thumbnail.setImageResource(R.drawable.moon_cloud)
                "04d", "13d", "50d",  "04n", "13n", "50n" -> thumbnail.setImageResource(R.drawable.cloud)
            }

            itemView.setOnClickListener {
                onItemClicked(city)
            }

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
                holder.bind(lista[position], onItemClicked)
            }
        }
    }

    override fun getItemCount() = lista.size
}