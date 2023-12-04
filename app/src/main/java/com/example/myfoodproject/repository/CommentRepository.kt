import com.google.android.gms.tasks.Tasks
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class CommentRepository {

    data class Comment(
        val commentId: String = "",
        val userId: String = "",
        val commentcontent: String = "",
        val commenttimestamp: String = "",
        val postId: String = ""
    )

    private val mDbRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("comments")

    // 댓글 작성 함수
    fun addComment(postId: String, commentcontent: String, callback: (Boolean, String?) -> Unit) {
        val commentId = mDbRef.push().key
        val userId = FirebaseAuth.getInstance().currentUser?.uid!!

        if (commentId != null) {
            val comment = Comment(
                commentId = commentId,
                userId = userId,
                commentcontent = commentcontent,
                commenttimestamp = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
                postId = postId
            )

            mDbRef.child(commentId).setValue(comment)
                .addOnCompleteListener { task ->
                    if (task.isSuccessful) {
                        callback.invoke(true, "댓글이 작성되었습니다.")
                    } else {
                        callback.invoke(false, "댓글을 작성하는데 실패했습니다.")
                    }
                }
        } else {
            callback.invoke(false, "댓글을 작성하는데 실패했습니다.")
        }
    }

    // 댓글 삭제 함수
    fun deleteComment(commentId: String, callback: (Boolean, String?) -> Unit) {
        mDbRef.child(commentId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val comment = snapshot.getValue(Comment::class.java)

                if (comment != null && comment.userId == FirebaseAuth.getInstance().currentUser?.uid) {
                    // 댓글 작성자와 현재 사용자가 동일한 경우에만 삭제 수행
                    mDbRef.child(commentId).removeValue().addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            callback.invoke(true, "댓글이 삭제되었습니다.")
                        } else {
                            callback.invoke(false, "댓글을 삭제하는데 실패했습니다.")
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

    //게시물 삭제시 댓글도 삭제
    fun deleteCommentsForPost(postId: String, callback: (Boolean, String?) -> Unit) {
        mDbRef.orderByChild("postId").equalTo(postId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val comments = mutableListOf<Comment>()
                for (dataSnapshot in snapshot.children) {
                    val comment = dataSnapshot.getValue(Comment::class.java)
                    comment?.let { comments.add(it) }
                }

                // 댓글 삭제
                val deleteTasks = comments.map { comment ->
                    mDbRef.child(comment.commentId).removeValue()
                }

                // 모든 댓글 삭제가 완료되면 콜백 호출
                Tasks.whenAllComplete(deleteTasks)
                    .addOnCompleteListener { task ->
                        if (task.isSuccessful) {
                            callback.invoke(true, "게시물과 관련된 댓글이 모두 삭제되었습니다.")
                        } else {
                            callback.invoke(false, "게시물과 관련된 댓글 삭제 중 오류가 발생했습니다.")
                        }
                    }
            }

            override fun onCancelled(error: DatabaseError) {
                callback.invoke(false, "댓글을 불러오는데 실패했습니다.")
            }
        })
    }


    // 댓글 읽기 함수
    fun getComments(postId: String, callback: (List<Comment>) -> Unit) {
        mDbRef.orderByChild("postId").equalTo(postId).addValueEventListener(object : ValueEventListener {
            override fun onDataChange(snapshot: DataSnapshot) {
                val comments = mutableListOf<Comment>()
                for (dataSnapshot in snapshot.children) {
                    val comment = dataSnapshot.getValue(Comment::class.java)
                    comment?.let { comments.add(it) }
                }
                callback(comments)
            }

            override fun onCancelled(error: DatabaseError) {
            }
        })
    }
}
