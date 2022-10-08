package com.example.climatyweather.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.climatyweather.model.IViewProgress
import com.example.climatyweather.model.MainRepository
import com.example.climatyweather.model.WeatherApiResult
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class SearchViewModel(private val view: IViewProgress, private val repository: MainRepository): ViewModel() {

    val searchCity = MutableLiveData<WeatherApiResult>()
    val errorMessage = MutableLiveData<String>()

    fun fetchCity(city: String) {
        view.showProgress(true)
        val request = repository.fetchCity(city)

        request.enqueue(object : Callback<WeatherApiResult>{
            override fun onResponse(
                call: Call<WeatherApiResult>,
                response: Response<WeatherApiResult>
            ) {
                view.showProgress(false)
                if (response.isSuccessful) searchCity.postValue(response.body())
                else errorMessage.postValue("City not found")
            }

            override fun onFailure(call: Call<WeatherApiResult>, t: Throwable) {
                errorMessage.postValue("Server error")
            }

        })
    }
}