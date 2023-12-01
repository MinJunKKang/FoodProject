package com.example.myfoodproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommentViewModel: ViewModel() {
    private val _comment = MutableLiveData<String>("")
    val comment: LiveData<String> get() = _comment //함부로 바꿀 수 없는 데이터를 밖에서 공개

    val isTextEmpty = _comment.value?.get(0) == isNotEmpty() //공백일 경우 작성할 수 있는 걸로 설정하기 일단

}