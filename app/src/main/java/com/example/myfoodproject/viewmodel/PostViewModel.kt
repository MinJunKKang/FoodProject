package com.example.myfoodproject.viewmodel

import android.net.Uri
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.myfoodproject.repository.PostRepository

class PostViewModel : ViewModel() {

    private val _posts = MutableLiveData<List<PostRepository.Post>>()
    val posts: LiveData<List<PostRepository.Post>> get() = _posts

    private val postRepository = PostRepository()

    // 글 작성 함수
    fun addPost(title: String, content: String, rating: Float, imageUri: Uri?, callback: (Boolean, String?) -> Unit) {
        postRepository.addPost(title, content, rating, imageUri) { success, message ->
            callback.invoke(success, message)
        }
    }


    // 글 삭제 함수
    fun deletePost(postId: String, callback: (Boolean, String?) -> Unit) {
        postRepository.deletePost(postId) { success, message ->
            callback.invoke(success, message)
        }
    }


    // 게시물 가져오는 함수 추가
    fun observePosts() {
        postRepository.observePosts { posts ->
            _posts.value = posts
        }
    }
}
