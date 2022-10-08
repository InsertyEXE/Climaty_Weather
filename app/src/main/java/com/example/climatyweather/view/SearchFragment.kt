package com.example.climatyweather.view

import android.app.AlertDialog
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

import androidx.lifecycle.ViewModelProvider

import androidx.recyclerview.widget.LinearLayoutManager
import com.example.climatyweather.model.MainRepository
import com.example.climatyweather.R
import com.example.climatyweather.model.SearchAdapter
import com.example.climatyweather.databinding.FragmentSearchBinding
import com.example.climatyweather.model.IViewProgress
import com.example.climatyweather.model.WeatherApiResult
import com.example.climatyweather.rest.WeatherRetrofitConfig
import com.example.climatyweather.viewmodel.SearchViewModel
import com.example.climatyweather.viewmodel.SearchViewModelFactory
import kotlin.math.roundToInt

private val retrofitService = WeatherRetrofitConfig.getInstance()
private lateinit var viewModel: SearchViewModel
private var itemsCity: ArrayList<WeatherApiResult> = arrayListOf()

class SearchFragment : Fragment(R.layout.fragment_search), IViewProgress {

    private var binding: FragmentSearchBinding? = null

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding = FragmentSearchBinding.bind(view)

        binding?.searchRv?.layoutManager = LinearLayoutManager(requireContext())

        viewModel = ViewModelProvider(
            this, SearchViewModelFactory(this, MainRepository(retrofitService))
        ).get(
             SearchViewModel::class.java
        )

    }

    override fun onResume() {
        super.onResume()

        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
        })

        viewModel.searchCity.observe(viewLifecycleOwner, Observer {
            if (!itemsCity.contains(it)) itemsCity.add(it)
            binding?.searchRv?.adapter?.notifyDataSetChanged()
            binding?.searchRv?.adapter = SearchAdapter(itemsCity) { weather -> openDialog(weather) }
        })

        binding?.searchSrc?.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(submit: String?): Boolean {
                viewModel.fetchCity(submit.toString())
                return false
            }

            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }

        })

    }

    private fun openDialog(weather: WeatherApiResult) {

        val builder = AlertDialog.Builder(requireContext())
        val inflater = requireActivity().layoutInflater.inflate(R.layout.fragment_home, null)

        val txtCity = inflater.findViewById<TextView>(R.id.home_txt_city)
        val imgTemp = inflater.findViewById<ImageView>(R.id.home_img_now)
        val txtTemp = inflater.findViewById<TextView>(R.id.home_txt_temp)
        val txtWeather = inflater.findViewById<TextView>(R.id.home_txt_weather)
        val txtStatus = inflater.findViewById<TextView>(R.id.home_txt_stats)
        val txtFeelsLike = inflater.findViewById<TextView>(R.id.home_txt_feelLike)
        val txtHumidity = inflater.findViewById<TextView>(R.id.home_txt_humidity)
        val txtWind = inflater.findViewById<TextView>(R.id.home_txt_wind)

        txtCity.text = weather.name
        txtTemp.text = "${weather.main.temp.roundToInt()}C°"
        txtWeather.text = weather.weather[0].main
        txtStatus.text = weather.weather[0].description
        txtFeelsLike.text = "${weather.main.feels_like.roundToInt()}C°"
        txtHumidity.text = "${weather.main.humidity}%"
        txtWind.text = "${weather.wind.speed} m/s"

        when (weather.weather[0].icon) {
            "09d", "10d", "11d", "09n", "10n", "11n" -> imgTemp.setImageResource(
                R.drawable.rain
            )
            "01d" -> imgTemp.setImageResource(R.drawable.sun)
            "02d", "03d" -> imgTemp.setImageResource(R.drawable.sun_cloud)
            "01n" -> imgTemp.setImageResource(R.drawable.moon)
            "02n", "03n" -> imgTemp.setImageResource(R.drawable.moon_cloud)
            "04d", "13d", "50d", "04n", "13n", "50n" -> imgTemp.setImageResource(
                R.drawable.cloud
            )
        }

        builder.setView(inflater)
        builder.create()
        builder.show()

    }

    override fun showProgress(enabled: Boolean) {
        if (enabled) binding?.progressCircular?.visibility = View.VISIBLE
        else binding?.progressCircular?.visibility = View.GONE
    }
}