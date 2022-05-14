package com.example.climatyweather.model

data class WeatherApiResult(
    val coord: Coordiante,
    val weather: List<Weather>,
    val main: Main,
    val wind: Wind,
    val name: String
)

data class Wind(
    val speed: Float
    )

data class Weather(
    val id: Int,
    val main: String,
    val description: String,
    val icon: String
)

data class Coordiante(
    val lon: Double,
    val lat: Double
)

data class Main(
    val temp: Float,
    val feels_like: Float,
    val humidity: Int,
)