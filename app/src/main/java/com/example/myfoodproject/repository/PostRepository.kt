package com.example.myfoodproject.repository

import android.net.Uri
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

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
        // 가게에 대한 평점
        val rating: Float,
        // 이미지 URL 저장할 필드
        val imageUrl: String?,
        // 게시 시간 등의 타임스탬프
        val timestamp: String
    )

    private val mDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("posts")
    private val mStorageRef: StorageReference = FirebaseStorage.getInstance().getReference("post_images")
    // 글 작성 함수
    fun addPost(title: String, content: String, rating: Float, imageUri: Uri?, callback: (Boolean, String?) -> Unit) {
        val postId = mDbRef.push().key
        val userId = FirebaseAuth.getInstance().currentUser?.uid!!

        if (postId != null) {
            val post = Post(
                postId = postId,
                userId = userId,
                title = title,
                content = content,
                rating = rating,
                imageUrl = null,
                timestamp = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) // 타임스탬프 형식 변경
            )

            mDbRef.child(postId).setValue(post)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        // 이미지 업로드
                        if (imageUri != null) {
                            uploadImage(postId, imageUri) { imageUrl ->
                                // 이미지 업로드 성공 시 imageUrl을 포함한 Post를 다시 저장
                                val updatePost = post.copy(imageUrl = imageUrl)
                                mDbRef.child(postId).setValue(updatePost)
                                    .addOnCompleteListener { innerTask ->
                                        if (innerTask.isSuccessful) {
                                            callback.invoke(true, "게시글이 작성되었습니다.")
                                        } else {
                                            callback.invoke(false, "게시글을 작성하는데 실패했습니다.")
                                        }
                                    }
                            }
                        } else {
                            callback.invoke(true, "게시글이 작성되었습니다.")
                        }
                    } else {
                        callback.invoke(false, "게시글을 작성하는데 실패했습니다.")
                    }
                }
        } else {
            callback.invoke(false, "게시글을 작성하는데 실패했습니다.")
        }
    }

    private fun uploadImage(postId: String, imageUri: Uri, callback: (String) -> Unit) {
        val imageRef = mStorageRef.child("$postId.jpg")
        imageRef.putFile(imageUri)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    imageRef.downloadUrl
                        .addOnSuccessListener { uri ->
                            // 이미지 업로드 성공 시 이미지의 다운로드 URL을 콜백으로 전달
                            callback.invoke(uri.toString())
                        }
                        .addOnFailureListener {
                            // 이미지 업로드 실패 시 처리
                            callback.invoke("")
                        }
                } else {
                    // 이미지 업로드 실패 시 처리
                    callback.invoke("")
                }
            }
    }

    // 게시글 삭제
    fun deletePost(postId: String, callback: (Boolean, String?) -> Unit) {
        mDbRef.child(postId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val post = snapshot.getValue(Post::class.java)

                if (post != null && post.userId == FirebaseAuth.getInstance().currentUser?.uid) {
                    // 글 작성자와 현재 사용자가 동일한 경우에만 삭제 수행
                    mDbRef.child(postId).removeValue()
                        .addOnCompleteListener { task ->
                            if (task.isSuccessful) {
                                callback.invoke(true, "게시글이 삭제되었습니다.")
                            } else {
                                callback.invoke(false, "게시글을 삭제하는데 실패했습니다.")
                            }
                        }
                } else {
                    callback.invoke(false, "해당 글을 삭제할 권한이 없습니다.")
                }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(false, "데이터를 불러오는데 실패했습니다.")
            }
        })
    }

}