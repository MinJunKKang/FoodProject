package com.example.myfoodproject.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.EmailAuthProvider
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

    // 회원가입 함수
    fun signUp(nick: String, name: String, email: String, password: String, callback: (Boolean, String?) -> Unit) {
        // 널 값 처리
        if (nick.isBlank() || name.isBlank() || email.isBlank() || password.isBlank()) {
            callback.invoke(false, "모든 항목을 입력해주세요.")
            return
        }

        mAuth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    addUserToDatabase(nick, name, email, mAuth.currentUser?.uid!!)
                    callback.invoke(true, "회원가입 되었습니다.")
                } else {
                    callback.invoke(false, "회원가입에 실패했습니다. 다시 시도해주세요.")
                }
            }
    }

    // db에 회원 정보 추가
    private fun addUserToDatabase(nick: String, name:String, email: String, uId: String) {
        mDbRef.child(uId).setValue(User(nick, name, email, uId))
    }

    // 로그인 함수
    fun login(email: String, password: String, callback: (Boolean, String?) -> Unit) {
        // 널 값 처리
        if (email.isBlank() || password.isBlank()) {
            callback.invoke(false, "이메일과 비밀번호를 모두 입력해주세요.")
            return
        }

        mAuth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    callback.invoke(true, null)
                } else {
                    callback.invoke(false, "로그인에 실패했습니다. 이메일 또는 비밀번호를 확인해주세요.")
                }
            }
    }

    // 로그아웃 함수
    fun logout() {
        mAuth.signOut()
    }

    // 계정 탈퇴 함수
    fun deleteUser(callback: (Boolean, String?) -> Unit) {
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            currentUser.delete()
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 사용자 정보 삭제 성공
                        mDbRef.child(currentUser.uid).removeValue()
                        callback.invoke(true, null)
                    } else {
                        // 사용자 정보 삭제 실패
                        callback.invoke(false, "계정 탈퇴에 실패했습니다.")
                    }
                }
        } else {
            callback.invoke(false, "로그인된 사용자 정보를 가져올 수 없습니다.")
        }
    }


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

    // FirebaseUser 가져오는 함수
    fun getCurrentUserLiveData(): LiveData<FirebaseUser?> {
        val currentUserLiveData = MutableLiveData<FirebaseUser?>()

        // mAuth.currentUser를 이용하여 LiveData를 업데이트하는 코드 추가
        mAuth.currentUser?.let { currentUserLiveData.postValue(it) }

        return currentUserLiveData
    }

    // User의 nick 변경하는 함수
    fun updateNickname(newNickname: String, callback: (Boolean, String?) -> Unit) {
        val currentUser = mAuth.currentUser
        if (currentUser != null) {
            mDbRef.child(currentUser.uid).child("nick").setValue(newNickname)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback.invoke(true, null)
                    } else {
                        callback.invoke(false, "닉네임을 업데이트하는 데 실패했습니다.")
                    }
                }
        } else {
            callback.invoke(false, "사용자 정보를 가져올 수 없습니다.")
        }
    }

    // User의 비밀번호 변경하는 함수
    fun updatePassword(currentPassword: String, newPassword: String, callback: (Boolean, String?) -> Unit) {
        val currentUser = mAuth.currentUser

        if (currentUser != null) {
            // 현재 비밀번호 확인
            val credential = EmailAuthProvider.getCredential(currentUser.email!!, currentPassword)
            currentUser.reauthenticate(credential)
                .addOnCompleteListener { reauthTask ->
                    if (reauthTask.isSuccessful) {
                        // 현재 비밀번호 확인 성공하면 비밀번호 변경 진행
                        currentUser.updatePassword(newPassword)
                            .addOnCompleteListener { updateTask ->
                                if (updateTask.isSuccessful) {
                                    callback.invoke(true, "비밀번호가 변경되었습니다.")
                                } else {
                                    callback.invoke(false, "비밀번호 변경에 실패했습니다. 다시 시도해주세요.")
                                }
                            }
                    } else {
                        callback.invoke(false, "현재 비밀번호를 확인할 수 없습니다. 다시 시도해주세요.")
                    }
                }
        } else {
            callback.invoke(false, "사용자 정보를 가져올 수 없습니다.")
        }
    }
}