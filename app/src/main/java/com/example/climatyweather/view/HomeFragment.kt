package com.example.climatyweather.view

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
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
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import kotlin.math.roundToInt


const val LOCALITION_PERMISSON_CODE = 1000
private val retrofitService = WeatherRetrofitConfig.getInstance()
private lateinit var lat: String
private lateinit var lon: String
private lateinit var viewModel: MainViewModel

class HomeFragment : Fragment(R.layout.fragment_home) {

    private var binding: FragmentHomeBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentHomeBinding.bind(view)

        permissions()
        locationPhone()

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(retrofitService))
        ).get(
            MainViewModel::class.java
        )

    }

    override fun onStart() {
        super.onStart()


        viewModel.errorMessage.observe(this, Observer{
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })


        viewModel.city.observe(this, Observer { weather ->

            binding?.homeTxtCity?.text = weather.name

            binding?.homeTxtWeather?.text = weather.weather[0].main
            binding?.homeTxtStats?.text = weather.weather[0].description

            binding?.homeTxtTemp?.text = "${weather.main.temp.roundToInt()}C°"
            binding?.homeTxtFeelLike?.text = "${weather.main.feels_like.roundToInt()}C°"
            binding?.homeTxtHumidity?.text = "${weather.main.humidity}%"
            binding?.homeTxtWind?.text = "${weather.wind.speed} m/s"


        })

        //TODO: click and change Celsium to fahrenheit


    }



    private fun permissions() {
        if (!isPermissionGranted())
            requestLocationPermission()
    }


    private fun locationPhone() {
        val localition = FusedLocationProviderClient(requireContext())
        val fosuedLocationProvider =
            LocationServices.getFusedLocationProviderClient(requireContext())

        //required code to use locationServices
        if (ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                requireContext(),
                Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED)

        fosuedLocationProvider.lastLocation.addOnSuccessListener {
            lat = it.latitude.toString()
            lon = it.longitude.toString()
            viewModel.locationPhone(lat, lon)
        }

        localition.lastLocation.addOnSuccessListener {
            lat = it.latitude.toString()
            lon = it.longitude.toString()

            viewModel.locationPhone(lat, lon)
        }

    }

    private fun isPermissionGranted(): Boolean {
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestLocationPermission() {

        return ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
            LOCALITION_PERMISSON_CODE
        )
    }
}