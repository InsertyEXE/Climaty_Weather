package com.example.climatyweather.rest

import com.example.climatyweather.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path

interface WeatherRetrofitConfig {

    @GET("weather?q={city}units=metric&appid={api_key}lang=pt_br")
    fun fetchCity(@Path("city") city: String): Call<WeatherApiResult>

    companion object {

        val retrofitService: WeatherRetrofitConfig by lazy {

            val retrofit = Retrofit.Builder()
                .baseUrl("https://api.openweathermap.org/data/2.5/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()

            retrofit.create(WeatherRetrofitConfig::class.java)

        }

        fun getInstance(): WeatherRetrofitConfig{
            return retrofitService
        }

    }
}