package com.example.climatyweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climatyweather.MainRepository

class SearchViewModelFactory(private val repository: MainRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(this.repository) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }

}