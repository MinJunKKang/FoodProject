package com.example.myfoodproject.repository

import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase

class PostRepository {
    data class Post(
        // 고유한 게시글 식별자
        val postId: String,
        // 게시글을 작성한 사용자의 ID
        val userId: String,
        // 게시글 제목
        val title: String,
        // 게시글 내용
        val content: String,
        // 게시 시간 등의 타임스탬프
        val timestamp: Long
    )

    private val mDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("posts")

    // 글 작성 함수
    fun addPost(title: String, content: String, callback: (Boolean, String?) -> Unit) {
        val postId = mDbRef.push().key
        if (postId != null) {
            val post = Post(
                postId = postId,
                userId = "",  // 사용자 ID는 여기서 적절한 값을 넣어주세요
                title = title,
                content = content,
                timestamp = System.currentTimeMillis()
            )
            mDbRef.child(postId).setValue(post)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback.invoke(true, "게시글이 작성되었습니다.")
                    } else {
                        callback.invoke(false, "게시글을 작성하는데 실패했습니다.")
                    }
                }
        } else {
            callback.invoke(false, "게시글을 작성하는데 실패했습니다.")
        }
    }
}