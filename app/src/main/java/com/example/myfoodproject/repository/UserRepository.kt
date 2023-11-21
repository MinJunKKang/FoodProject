package com.example.myfoodproject.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.database.database

class UserRepository {
    data class User (
        val nick: String,
        val name: String,
        val email: String,
        val uId: String,
    ) {
        constructor(): this("", "", "", "")
    }
}