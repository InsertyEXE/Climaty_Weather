package com.example.climatyweather

import com.example.climatyweather.rest.WeatherRetrofitConfig

class MainRepository(private val retrofitService: WeatherRetrofitConfig) {

    fun fetchCity(city: String) = retrofitService.fetchCity(city)

    fun fetchLocationPhone(lat: Double, lon: Double) = retrofitService.fetchLocationPhone(lat, lon)
}