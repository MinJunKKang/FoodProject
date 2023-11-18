package com.example.myfoodproject

enum class FoodType{
    RAMEN,
    SUSHI,
    TONKATSU
}
data class Restaurant(val foodtype: FoodType,
                      val name: String,
                      val rate: Double,
                      val review: Int)