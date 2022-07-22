package com.example.climatyweather.view

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.location.Criteria
import android.location.Location
import android.location.LocationListener
import android.location.LocationManager
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.climatyweather.R
import com.example.climatyweather.databinding.FragmentHomeBinding
import com.example.climatyweather.model.MainRepository
import com.example.climatyweather.rest.WeatherRetrofitConfig
import com.example.climatyweather.viewmodel.MainViewModel
import com.example.climatyweather.viewmodel.MainViewModelFactory
import com.google.android.gms.location.FusedLocationProviderClient
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

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(MainRepository(retrofitService))
        ).get(
            MainViewModel::class.java
        )

    }

    override fun onResume() {
        super.onResume()

        permissions()
        locationPhone()

        viewModel.city.observe(viewLifecycleOwner, Observer { weather ->

            binding?.let {
                with(it) {
                    homeTxtCity.text = weather.name

                    homeTxtWeather.text = weather.weather[0].main
                    homeTxtStats.text = weather.weather[0].description

                    homeTxtTemp.text = "${weather.main.temp.roundToInt()}C°"
                    homeTxtFeelLike.text = "${weather.main.feels_like.roundToInt()}C°"
                    homeTxtHumidity.text = "${weather.main.humidity}%"
                    homeTxtWind.text = "${weather.wind.speed} m/s"

                    when (weather.weather[0].icon) {
                        "09d", "10d", "11d", "09n", "10n", "11n" -> binding?.homeImgNow?.setImageResource(
                            R.drawable.rain
                        )
                        "01d" -> binding?.homeImgNow?.setImageResource(R.drawable.sun)
                        "02d", "03d" -> binding?.homeImgNow?.setImageResource(R.drawable.sun_cloud)
                        "01n" -> binding?.homeImgNow?.setImageResource(R.drawable.moon)
                        "02n", "03n" -> binding?.homeImgNow?.setImageResource(R.drawable.moon_cloud)
                        "04d", "13d", "50d", "04n", "13n", "50n" -> binding?.homeImgNow?.setImageResource(
                            R.drawable.cloud
                        )
                    }
                }
            }
        })

        viewModel.errorMessage.observe(this, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

    }

    private fun permissions() {
        if (!isPermissionGranted())
            requestLocationPermission()
    }


    @SuppressLint("MissingPermission")
    private fun locationPhone() {
        val location = FusedLocationProviderClient(requireContext())

        val service = requireContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val criteria = Criteria()
        val bestProvider = service.getBestProvider(criteria, true).toString()

        location.lastLocation.addOnSuccessListener {

            //getting location if there is some already available
            if (it != null){
                lon = it.longitude.toString()
                lat = it.latitude.toString()
                viewModel.locationPhone(lat, lon)
            }

            else {
                /*
                Getting actually location if does not
                exist one already prepared in cache phone
                 */
                service.requestLocationUpdates(bestProvider, 1000, 0f, object : LocationListener {
                    override fun onLocationChanged(location: Location) {
                        lat = location.latitude.toString()
                        lon = location.longitude.toString()
                        viewModel.locationPhone(lat, lon)
                    }

                    //obligatory func to execute
                    override fun onStatusChanged(provider: String?, status: Int, extras: Bundle?) {
                        super.onStatusChanged(provider, status, extras)
                    }
                })
            }

        }

    }


    private fun requestLocationPermission() {
        return ActivityCompat.requestPermissions(
            requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
            ),
            LOCALITION_PERMISSON_CODE
        )
    }

    private fun isPermissionGranted(): Boolean {

        return ActivityCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}
