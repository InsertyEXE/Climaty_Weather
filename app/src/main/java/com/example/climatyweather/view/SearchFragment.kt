package com.example.climatyweather.view

import android.os.Bundle
import android.view.View
import android.widget.SearchView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climatyweather.MainRepository
import com.example.climatyweather.R
import com.example.climatyweather.model.SearchAdapter
import com.example.climatyweather.databinding.FragmentSearchBinding
import com.example.climatyweather.model.WeatherApiResult
import com.example.climatyweather.rest.WeatherRetrofitConfig
import com.example.climatyweather.viewmodel.SearchViewModel
import com.example.climatyweather.viewmodel.SearchViewModelFactory
import kotlinx.android.synthetic.main.fragment_search.view.*

private val retrofitService = WeatherRetrofitConfig.getInstance()
private lateinit var viewModel: SearchViewModel
private var itemsCity:  ArrayList<WeatherApiResult> = arrayListOf()

class SearchFragment : Fragment(R.layout.fragment_search) {

    private var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(
            this, SearchViewModelFactory(MainRepository(retrofitService))
        ).get(
            SearchViewModel::class.java
            )

        viewModel.searchCity.observe(viewLifecycleOwner, Observer {

            itemsCity.add(it)
            binding?.searchRv?.adapter = SearchAdapter(itemsCity)

        })

    }

    override fun onResume() {
        super.onResume()

        binding?.searchSrc?.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(submit: String?): Boolean {
                viewModel.fetchCity(submit.toString())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
               // viewModel.fetchCity(query.toString())
                return false
            }

        })

    }
}