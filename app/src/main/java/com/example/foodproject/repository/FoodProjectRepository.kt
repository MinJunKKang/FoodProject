package com.example.foodproject.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.ktx.database
import com.google.firebase.ktx.Firebase

class FoodProjectRepository {
    val database = Firebase.database
    val userRef = database.getReference("user")

    fun observeFoodProject(restaurant: MutableLiveData<String>){
        userRef.addValueEventListener(object: ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot){
                restaurant.postValue(snapshot.value.toString())
            }

            override fun onCancelled(error: DatabaseError) {

            }
        })
    }
}