package com.example.myfoodproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfoodproject.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel: ViewModel() {

    private val _userInfo = MutableLiveData<UserRepository.User?>()
    val userInfo: LiveData<UserRepository.User?> get() = _userInfo

    private val userrepository = UserRepository()


    // 회원가입 함수
    fun signUp(nick: String, name: String, email: String, password: String, callback: (Boolean) -> Unit) {
        userrepository.signUp(nick, name, email, password, callback)
    }

    // 로그인 함수
    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        userrepository.login(email, password) { success ->
            callback.invoke(success)
        }
    }

    // FirebaseUser 가져오는 함수
    fun getCurrentUser(): LiveData<FirebaseUser?> {
        return userrepository.getCurrentUserLiveData()
    }


    // 사용자 정보 가져오는 함수
    fun observeUser(userId: String) {
        userrepository.observeUser(userId) { user ->
            _userInfo.postValue(user)
        }
    }


    fun logout() {
        userrepository.logout()
    }

}