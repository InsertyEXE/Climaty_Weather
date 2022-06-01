package com.example.climatyweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climatyweather.MainRepository
import com.example.climatyweather.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val repository: MainRepository): ViewModel() {

    val searchCity = MutableLiveData<List<WeatherApiResult>>()
    val errorMessage = MutableLiveData<String>()


    fun fetchCity(city: String) {

        val request = repository.fetchCity(city)

        request.enqueue(object : Callback<List<WeatherApiResult>>{
            override fun onResponse(
                call: Call<List<WeatherApiResult>>,
                response: Response<List<WeatherApiResult>>
            ) {
                if (response.isSuccessful)
                    searchCity.postValue(response.body())
            }

            override fun onFailure(call: Call<List<WeatherApiResult>>, t: Throwable) {
                errorMessage.postValue("Server error")
            }

        })
    }
}