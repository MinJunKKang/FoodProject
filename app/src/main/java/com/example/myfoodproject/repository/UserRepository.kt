package com.example.myfoodproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseUser
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener


class UserRepository {
    data class User (
        val nick: String,
        val name: String,
        val email: String,
        val uId: String,
    ) {
        constructor(): this("", "", "", "")
    }


    private val mAuth: FirebaseAuth = FirebaseAuth.getInstance()
    private val mDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("user")

    // User의 정보가 변하는지 관찰하는 함수
    fun observeUser(userId: String, callback: (User?) -> Unit) {
        mDbRef.child(userId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val user = snapshot.getValue(User::class.java)
                callback.invoke(user)
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(null)
            }
        })
    }

    // 회원가입 함수
    fun signUp(nick: String, name: String, email: String, password: String, callback: (Boolean) -> Unit) {
        // 널 값 처리
        if (nick.isBlank() || name.isBlank() || email.isBlank() || password.isBlank()) {
            callback.invoke(false)
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(nick, name, email, mAuth.currentUser?.uid!!)
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }
    }

    // db에 회원 정보 추가
    private fun addUserToDatabase(nick: String, name:String, email: String, uId: String) {
        mDbRef.child("user").child(uId).setValue(User(nick, name, email, uId))
    }

    // 로그인 함수
    fun login(email: String, password: String, callback: (Boolean) -> Unit) {
        // 널 값 처리
        if (email.isBlank() || password.isBlank()) {
            callback.invoke(false)
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.invoke(true)
                } else {
                    callback.invoke(false)
                }
            }
    }

    // 로그아웃 함수
    fun logout() {
        mAuth.signOut()
    }

    // FirebaseUser 가져오는 함수
    fun getCurrentUserLiveData(): LiveData<FirebaseUser?> {
        val currentUserLiveData = MutableLiveData<FirebaseUser?>()

        // mAuth.currentUser를 이용하여 LiveData를 업데이트하는 코드 추가
        mAuth.currentUser?.let { currentUserLiveData.postValue(it) }

        return currentUserLiveData
    }

}