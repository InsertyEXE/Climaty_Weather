package com.example.climatyweather.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climatyweather.MainRepository
import com.example.climatyweather.R
import com.example.climatyweather.databinding.FragmentHomeBinding
import com.example.climatyweather.rest.WeatherRetrofitConfig
import com.example.climatyweather.viewmodel.MainViewModel
import com.example.climatyweather.viewmodel.MainViewModelFactory

const val LOCALITION_PERMISSON_CODE = 1000
private val retrofitService = WeatherRetrofitConfig.getInstance()

class HomeFragment: Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(retrofitService))
        ).get(
            MainViewModel::class.java
        )

    }

    override fun onStart() {
        super.onStart()

        permissions()

        viewModel.fetchCity("franco da rocha")
        viewModel.city.observe(this, Observer { city ->
            binding?.homeTxtCity?.text = city
        })


    }

    private fun permissions() {
        if (!isPermissionGranted()) {
            requestLocationPermission()
        }
    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {
        return ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_LOCATION_EXTRA_COMMANDS),
            LOCALITION_PERMISSON_CODE
        )
    }
}