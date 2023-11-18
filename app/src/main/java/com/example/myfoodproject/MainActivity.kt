package com.example.myfoodproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.myfoodproject.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    val restaurants = arrayOf(
        Restaurant(FoodType.RAMEN,"라면의 신", 4.9, 1254),
        Restaurant(FoodType.RAMEN,"최고 라멘", 4.5, 841),
        Restaurant(FoodType.SUSHI,"스시 월드", 4.2, 566),
        Restaurant(FoodType.SUSHI,"장인 스시", 4.7, 1024),
        Restaurant(FoodType.TONKATSU,"맛있는 돈카츠", 4.1, 497),
        Restaurant(FoodType.TONKATSU,"돈카츠 천국", 4.4, 774),
    )

    lateinit var binding : ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.recRestaurants.layoutManager = LinearLayoutManager(this)
        binding.recRestaurants.adapter = RestaurantsAdapter(restaurants)
    }

}


