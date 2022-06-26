package com.example.climatyweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climatyweather.model.MainRepository
import com.example.climatyweather.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainViewModel(private val repository: MainRepository) : ViewModel() {

    var city = MutableLiveData<WeatherApiResult>()
    var errorMessage = MutableLiveData<String>()


    fun locationPhone(lat: String, lon: String) {

        val request = repository.fetchLocationPhone(lat, lon)


        request.enqueue(object : Callback<WeatherApiResult>{
            override fun onResponse(
                call: Call<WeatherApiResult>,
                response: Response<WeatherApiResult>
            ) {
                if (response.isSuccessful)
                    city.postValue(response.body())
                else
                    errorMessage.postValue("Location Not Found")
            }

            override fun onFailure(call: Call<WeatherApiResult>, t: Throwable) {
                errorMessage.postValue("Server error")
            }

        })

    }

}