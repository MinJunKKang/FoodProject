package com.example.foodproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.foodproject.FoodType
import com.example.foodproject.Restaurant
import com.example.foodproject.repository.FoodProjectRepository

val restaurants = arrayOf(
    Restaurant(FoodType.RAMEN,"라멘의 신", 4.9, 1254),
    Restaurant(FoodType.RAMEN,"최고 라멘", 4.5, 841),
)
class FoodProjectViewModel: ViewModel() {
    private val repository = FoodProjectRepository()


    private val _list = MutableLiveData<String>()
    val list : LiveData<String> get() = _list



}