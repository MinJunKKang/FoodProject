package com.example.myfoodproject.viewmodel

import CommentRepository
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class CommentViewModel: ViewModel() {
    private val _comments = MutableLiveData<List<CommentRepository.Comment>>()
    val comments: LiveData<List<CommentRepository.Comment>> get() = _comments //함부로 바꿀 수 없는 데이터를 밖에서 공개

    val commentRepository = CommentRepository()


    // 댓글 작성 함수
    fun addComment(postId: String, commentcontent: String, callback: (Boolean, String?) -> Unit) {
        commentRepository.addComment(postId, commentcontent) { success, message ->
            callback.invoke(success, message)
        }
    }


    // 댓글 삭제 함수
    fun deleteComment(commentId: String, callback: (Boolean, String?) -> Unit) {
        commentRepository.deleteComment(commentId) { success, message ->
            callback.invoke(success, message)
        }
    }

    // 댓글 읽기 함수
    fun getComments(postId: String) {
        commentRepository.getComments(postId) { comments ->
            _comments.postValue(comments)
        }
    }

}