package com.example.climatyweather

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.climatyweather.databinding.FragmentSearchBinding

class SearchFragment: Fragment(R.layout.fragment_search) {

     var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)
    }
}