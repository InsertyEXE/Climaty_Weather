package com.example.climatyweather.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.climatyweather.model.IViewProgress
import com.example.climatyweather.model.MainRepository

class SearchViewModelFactory(private val view: IViewProgress, private val repository: MainRepository): ViewModelProvider.Factory{

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(SearchViewModel::class.java)) {
            return SearchViewModel(this.view,this.repository) as T
        }
        throw IllegalArgumentException("ViewModel Not Found")
    }

}