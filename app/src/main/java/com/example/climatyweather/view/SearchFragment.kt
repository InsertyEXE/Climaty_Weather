package com.example.climatyweather.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climatyweather.MainRepository
import com.example.climatyweather.R
import com.example.climatyweather.model.SearchAdapter
import com.example.climatyweather.databinding.FragmentSearchBinding
import com.example.climatyweather.rest.WeatherRetrofitConfig
import com.example.climatyweather.viewmodel.SearchViewModel
import com.example.climatyweather.viewmodel.SearchViewModelFactory

private val retrofitService = WeatherRetrofitConfig.getInstance()
private lateinit var viewModel: SearchViewModel

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRv?.adapter = SearchAdapter()


        viewModel = ViewModelProvider(
            this, SearchViewModelFactory(MainRepository(retrofitService))
        ).get(
            SearchViewModel::class.java
            )
    }

    override fun onResume() {
        super.onResume()


    }
}