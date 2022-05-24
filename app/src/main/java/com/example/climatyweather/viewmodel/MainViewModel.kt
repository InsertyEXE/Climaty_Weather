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

    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()

    fun fetchCity(citySearch: String){

        val request = repository.fetchCity(citySearch)


        request.enqueue(object : Callback<WeatherApiResult>{
            override fun onResponse(
                call: Call<WeatherApiResult>,
                response: Response<WeatherApiResult>
            ) {
                if (response.isSuccessful){
                    val body = response.body()
                    city.postValue(body)
                }
                else{
                    errorMessage.postValue("City Not Found")
                }

            }
            override fun onFailure(call: Call<WeatherApiResult>, t: Throwable) {
                errorMessage.postValue("Server")
            }

        })



    }
}