package com.example.climatyweather.view

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import com.example.climatyweather.R
import com.example.climatyweather.databinding.FragmentFavoriteBinding

class FavoriteFragment: Fragment(R.layout.fragment_favorite) {

    private var binding: FragmentFavoriteBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentFavoriteBinding.bind(view)
    }
}