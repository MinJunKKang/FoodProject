package com.example.myfoodproject.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfoodproject.repository.PostRepository

class PostViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<PostRepository.Post>>()
    val posts: LiveData<List<PostRepository.Post>> get() = _posts

    private val postRepository = PostRepository()

    // 글 작성 함수
    fun addPost(title: String, content: String, callback: (Boolean, String?) -> Unit) {
        postRepository.addPost(title, content) { success, message ->
            callback.invoke(success, message)
        }
    }
}
