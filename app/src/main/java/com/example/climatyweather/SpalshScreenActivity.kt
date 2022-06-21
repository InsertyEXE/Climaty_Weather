package com.example.climatyweather

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.climatyweather.databinding.ActivitySpalshScreenBinding
import com.example.climatyweather.view.MainActivity

class SpalshScreenActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySpalshScreenBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySpalshScreenBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.splashImg.animate().apply {

            setListener(object : AnimatorListenerAdapter(){
                override fun onAnimationEnd(animation: Animator?) {
                    super.onAnimationEnd(animation)
                    val intent = Intent(this@SpalshScreenActivity, MainActivity::class.java)
                    startActivity(intent)
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)
                }
            })

            duration = 1000
            alpha(1.0f)
            start()
        }
    }
}