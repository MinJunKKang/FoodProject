package com.example.myfoodproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfoodproject.repository.UserRepository

class UserViewModel: ViewModel() {
    private val _userId = MutableLiveData<String>()
    private val _userPassword = MutableLiveData<String>()

    val userId: LiveData<String>
        get() = _userId
    val userPassword: LiveData<String>
        get() = _userPassword

    private val repository = UserRepository()

    init {
    }

    fun updateUserInfo(userId: String, userPassword: String) {
        _userId.value = userId
        _userPassword.value = userPassword
    }
}