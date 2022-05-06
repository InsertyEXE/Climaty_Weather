package com.example.climatyweather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.climatyweather.databinding.ActivityMainBinding

private lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*
        Tela para pesqusiar cidades
        tela para mostras os status local
        Lista de locais favoritos
         */
    }
}