package com.example.climatyweather.viewmodel

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climatyweather.MainRepository
import com.example.climatyweather.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(private val repository: MainRepository): ViewModel() {

    var city = MutableLiveData<String>()

    fun fetchCity(citySearch: String){

        val request = repository.fetchCity(citySearch)

        request.enqueue(object : Callback<WeatherApiResult>{
            override fun onResponse(
                call: Call<WeatherApiResult>,
                response: Response<WeatherApiResult>
            ) {

                val body = response.body()
                city.postValue(body?.name.toString())
                Log.i("city", body.toString())
            }
            override fun onFailure(call: Call<WeatherApiResult>, t: Throwable) {
                //errormessage
            }

        })



    }
}