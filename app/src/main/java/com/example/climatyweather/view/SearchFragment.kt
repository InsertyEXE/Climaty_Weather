package com.example.climatyweather.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climatyweather.R
import com.example.climatyweather.model.SearchAdapter
import com.example.climatyweather.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search) {

     var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())
        binding?.searchRv?.adapter = SearchAdapter()
    }
}