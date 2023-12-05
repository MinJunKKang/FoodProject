package com.example.myfoodproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfoodproject.repository.UserRepository
import com.google.firebase.auth.FirebaseUser

class UserViewModel: ViewModel() {

    private val _userInfo = MutableLiveData<UserRepository.User?>()
    val userInfo: LiveData<UserRepository.User?> get() = _userInfo

    val userRepository = UserRepository()


    // 회원가입 함수
    fun signUp(nick: String, name: String, email: String, password: String, callback: (Boolean, String?) -> Unit) {
        userRepository.signUp(nick, name, email, password) { success, message ->
            callback.invoke(success, message)
        }
    }

    // 로그인 함수
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        userRepository.login(email, password) { success, message ->
            callback.invoke(success, message)
        }
    }

    // FirebaseUser 가져오는 함수
    fun getCurrentUser(): LiveData<FirebaseUser?> {
        return userRepository.getCurrentUserLiveData()
    } // 너 바꿔


    // 사용자 정보 가져오는 함수
    fun observeUser(userId: String) {
        userRepository.observeUser(userId) { user ->
            _userInfo.postValue(user)
        }
    }

    // 닉네임 바꾸는 함수
    fun updateNickname(newNickname: String, callback: (Boolean, String?) -> Unit) {
        userRepository.updateNickname(newNickname) { success, message ->
            callback.invoke(success, message)
        }
    }

    // 비밀번호 바꾸는 함수
    fun updatePassword(currentPassword: String, newPassword: String, callback: (Boolean, String?) -> Unit) {
        userRepository.updatePassword(currentPassword, newPassword) { success, message ->
            callback.invoke(success, message)
        }
    }

    // 로그아웃 함수
    fun logout() {
        userRepository.logout()
    }

    fun deleteUser(callback: (Boolean, String?) -> Unit) {
        userRepository.deleteUser { success, message ->
            if (success) {
                _userInfo.postValue(null)
            }
            callback.invoke(success, message)
        }
    }


}